package com.aim.booking.security.service.impl;

import static com.aim.booking.service.exception.ErrorMessages.ACCESS_DENIED_CANNOT_UPDATE_BOOKING;
import static com.aim.booking.service.exception.ErrorMessages.ACCESS_DENIED_CANNOT_UPDATE_CREATE_OR_UPDATE;

import com.aim.booking.security.service.SecurityService;
import com.aim.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {

  private final BookingService bookingService;

  public SecurityServiceImpl(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @Override
  public boolean isCreator(String bookingId) {
    String userEmail = AuthenticationHelper.getCurrentUserEmail();
    if (!AuthenticationHelper.isUserSystemAdmin() && !bookingService.userIsCreator(bookingId,
        userEmail)) {
      throw new AccessDeniedException(
          String.format(ACCESS_DENIED_CANNOT_UPDATE_BOOKING, userEmail));
    }
    return true;
  }


  public boolean isHaveUpdatePermissions() {
    if (!AuthenticationHelper.isUserSystemAdmin()) {
      throw new AccessDeniedException(
          ACCESS_DENIED_CANNOT_UPDATE_CREATE_OR_UPDATE);
    }
    return true;
  }
}
