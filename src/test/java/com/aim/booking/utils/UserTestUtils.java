package com.aim.booking.utils;

import com.aim.booking.domain.UserDto;
import com.aim.booking.domain.mapper.UserMapper;
import com.aim.booking.persistence.entity.User;
import com.aim.booking.persistence.enums.UserStatus;
import com.aim.booking.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTestUtils {

  private final UserRepository userRepository;
  private final UserMapper userMapper;


  public UserTestUtils(UserRepository userRepository,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public UserDto buildUserDto(String firsName, String lastName, String email) {
    return UserDto.builder()
        .firstName(firsName)
        .lastName(lastName)
        .password("password")
        .email(email)
        .status(UserStatus.ACTIVE)
        .build();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public User createUserDbInstance(String firsName, String lastName, String email) {
    User user = userMapper.toUser(buildUserDto(firsName, lastName, email));
    return userRepository.save(user);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public User createUserDbInstance(String firsName, String lastName, String email,
      String userStatus) {
    User user = userMapper.toUser(buildUserDto(firsName, lastName, email));
    user.setStatus(UserStatus.getUserStatus(userStatus));
    return userRepository.save(user);
  }
}
