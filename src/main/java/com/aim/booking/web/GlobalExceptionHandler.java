package com.aim.booking.web;

import io.swagger.v3.oas.annotations.Hidden;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<BookingErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex) {
    log.debug("{} was thrown", ex.getClass(), ex);
    return new ResponseEntity<>(
        new BookingErrorResponse(
            StringUtils.join("Validation error", ": ", ex.getLocalizedMessage())),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<BookingErrorResponse> handleEntityNotFoundException(
      ConstraintViolationException ex) {
    log.debug("{} was thrown", ex.getClass(), ex);
    return new ResponseEntity<>(
        new BookingErrorResponse(ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<BookingErrorResponse> handleAccessDeniedException(
      ConstraintViolationException ex) {
    log.debug("{} was thrown", ex.getClass(), ex);
    return new ResponseEntity<>(
        new BookingErrorResponse(ex.getMessage()),
        HttpStatus.FORBIDDEN);
  }
}
