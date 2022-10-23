package com.aim.booking.persistence.enums;

import static java.lang.String.format;

import com.aim.booking.service.exception.ErrorMessages;
import java.util.HashMap;
import java.util.Map;
import org.webjars.NotFoundException;

public enum UserStatus {
  ACTIVE, INACTIVE;

  private static final Map<String, UserStatus> USER_STATUSES = new HashMap<>();

  static {
    for (UserStatus e : values()) {
      USER_STATUSES.put(e.name(), e);
    }
  }

  public static UserStatus getUserStatus(String status) {
    if (!USER_STATUSES.containsKey(status)) {
      throw new NotFoundException(format(ErrorMessages.USER_STATUS_IS_INCORRECT, status));
    }
    return USER_STATUSES.get(status);
  }

}
