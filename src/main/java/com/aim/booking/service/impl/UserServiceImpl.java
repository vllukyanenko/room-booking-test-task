package com.aim.booking.service.impl;

import com.aim.booking.domain.UserDto;
import com.aim.booking.domain.mapper.UserMapper;
import com.aim.booking.persistence.entity.User;
import com.aim.booking.persistence.enums.UserStatus;
import com.aim.booking.persistence.repository.UserRepository;
import com.aim.booking.service.UserService;
import com.aim.booking.service.exception.ErrorMessages;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;


  public UserServiceImpl(UserRepository userRepository,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public UserDto save(UserDto dto) {
    log.debug("Save user with email {}", dto.getEmail());
    User user = userMapper.toUser(dto);
    user = userRepository.save(user);
    return userMapper.toUserDto(user);
  }

  @Override
  public UserDto update(UserDto dto, String id) {
    log.debug("Update user with id {}", id);
    User user = userRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(
            String.format(ErrorMessages.USER_WITH_ID_DOES_NOT_EXIST, id))
    );
    userMapper.updateUserFromDto(dto, user);
    User fromDb = userRepository.save(user);
    return userMapper.toUserDto(fromDb);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto findById(String id) {
    log.debug("Find user by id {}", id);
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format(
            ErrorMessages.USER_WITH_ID_DOES_NOT_EXIST, id)));
    return userMapper.toUserDto(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> getAll() {
    log.debug("Get all the users");
    List<User> userList = userRepository.findAll();
    return userList.stream().map(
            userMapper::toUserDto)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(String id) {
    log.debug("Delete user by id {}", id);
    if (!userRepository.existsById(id)) {
      throw new EntityNotFoundException(
          String.format(ErrorMessages.USER_WITH_ID_DOES_NOT_EXIST, id));
    }
    userRepository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExistsById(String userId) {
    log.debug("Check user {} in database", userId);
    return userRepository.existsById(userId);
  }


  @Override
  @Transactional(readOnly = true)
  public boolean isExistsByEmailAndStatus(String email, UserStatus status) {
    log.debug("Check user with email and status {} in database", email);
    return userRepository.existsByEmailAndUserStatus(email, status);
  }

}
