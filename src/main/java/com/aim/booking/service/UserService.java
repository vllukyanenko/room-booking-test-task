package com.aim.booking.service;

import com.aim.booking.domain.UserDto;
import com.aim.booking.persistence.enums.UserStatus;
import java.util.UUID;

public interface UserService extends BaseService<UserDto> {

  boolean isExistsById(String userId);

  boolean isExistsByEmailAndStatus(String email, UserStatus status);

  UserDto findByEmail(String email);
}
