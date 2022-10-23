package com.aim.booking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aim.booking.annotation.BookingIntegrationTest;
import com.aim.booking.domain.UserDto;
import com.aim.booking.persistence.entity.User;
import com.aim.booking.persistence.enums.UserStatus;
import com.aim.booking.service.exception.ErrorMessages;
import com.aim.booking.utils.UserTestUtils;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@BookingIntegrationTest
public class UserServiceTest {

  @Autowired
  private UserService userService;
  @Autowired
  private UserTestUtils userTestUtils;

  private final String firstName = "FirstName";
  private final String lastName = "LastName";
  private final String email = "test@mail.com";

  @Test
  public void when_create_user_then_ReturnUserDto() {
    UserDto userDto = userTestUtils.buildUserDto(firstName, lastName, email);
    UserDto savedUserDto = userService.save(userDto);
    assertThat(savedUserDto.getEmail()).isEqualTo(userDto.getEmail());
  }

  @Test
  public void whenUpdateUser_thenReturnUserDto() {
    User savedUser = userTestUtils.createUserDbInstance(firstName, lastName, email);
    String firstName = "newFirst";
    String lastName = "newLast";
    String email = "new.test@test.com";
    String password = "newPassword";

    assertThat(firstName).isNotEqualTo(savedUser.getFirstName());
    assertThat(lastName).isNotEqualTo(savedUser.getLastName());

    UserDto dto = userService.findById(savedUser.getId());
    dto.setEmail(email);
    dto.setFirstName(firstName);
    dto.setLastName(lastName);
    dto.setPassword(password);

    UserDto result = userService.update(dto, savedUser.getId());
    assertThat(savedUser.getId()).isEqualTo(result.getId());
    assertThat(firstName).isEqualTo(result.getFirstName());
    assertThat(lastName).isEqualTo(result.getLastName());
  }

  @Test
  public void when_get_usersList_then_ReturnUserDtoList() {
    int usersAmount = 5;
    for (int i = 0; i < usersAmount; i++) {
      userTestUtils.createUserDbInstance(StringUtils.join(firstName, String.valueOf(i)),
          StringUtils.join(lastName, String.valueOf(i)),
          StringUtils.join("test", String.valueOf(i), "@mail.com"));
    }
    List<UserDto> savedUserDtoList = userService.getAll();
    assertThat(savedUserDtoList.size()).isEqualTo(usersAmount);
  }

  @Test
  public void when_findById_then_UserDto() {
    User savedUser = userTestUtils.createUserDbInstance(firstName, lastName, email);
    UserDto fetchedUser = userService.findById(savedUser.getId());
    assertThat(fetchedUser.getId()).isEqualTo(savedUser.getId());
    assertThat(fetchedUser.getEmail()).isEqualTo(savedUser.getEmail());
  }

  @Test
  public void when_deleteUserById_then_UserShouldBeDeleted() {
    User savedUser = userTestUtils.createUserDbInstance(firstName, lastName, email);
    userService.delete(savedUser.getId());

    Exception exception = assertThrows(EntityNotFoundException.class, () -> {
      userService.findById(savedUser.getId());
    });
    assertTrue(exception.getMessage()
        .contains(String.format(ErrorMessages.USER_WITH_ID_DOES_NOT_EXIST, savedUser.getId())));
  }

  @Test
  public void when_check_isExistUserById_then_return_True() {
    User savedUser = userTestUtils.createUserDbInstance(firstName, lastName, email);
    assertTrue(userService.isExistsById(savedUser.getId()));
  }

  @Test
  public void when_check_isExistUserByIdAndStatus_then_return_True() {
    String userStatus = "INACTIVE";
    userTestUtils.createUserDbInstance(firstName, lastName, email, userStatus);
    assertTrue(userService.isExistsByEmailAndStatus(email,
        UserStatus.getUserStatus(userStatus)));
  }
}
