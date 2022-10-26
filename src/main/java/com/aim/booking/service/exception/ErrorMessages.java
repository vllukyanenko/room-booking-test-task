package com.aim.booking.service.exception;

public class ErrorMessages {

  //USER
  public static final String USER_WITH_ID_DOES_NOT_EXIST = "User with id %s doesn't exist";
  public static final String USER_WITH_EMAIL_DOES_NOT_EXIST = "User with email %s doesn't exist";
  public static final String USER_STATUS_IS_INCORRECT = "User status %s is incorrect";


  //ROOM
  public static final String ROOM_WITH_ID_DOES_NOT_EXIST = "Room with id %s doesn't exist";
  public static final String ACCESS_DENIED_CANNOT_UPDATE_CREATE_OR_UPDATE = "You don't have permission for room creating/updating. Super Administrator has permission or room creating/updating";


  //BOOKING
  public static final String BOOKING_WITH_ID_DOES_NOT_EXIST = "Booking with id %s doesn't exist";
  public static final String BOOKING_TIME_ARE_OVERLAPPED = "Cannot book rooms. Time of booking are overlapped";
  public static final String BOOKING_OF_ROOM_IS_POSSIBLE_FROM_MN_TO_FR = "Room booking is possible from Monday to Friday";
  public static final String BOOKING_OF_ROOM_IS_POSSIBLE_FROM_9_TO_17 = "Room booking is possible from 9:00 to 17:00";
  public static final String MINIMAL_BOOKING_TIME_OF_ROOM_IS = "Minimal booking time of room is %s minutes";
  public static final String ACCESS_DENIED_CANNOT_UPDATE_BOOKING = "Cannot update booking. User with email %s doesn't have permissions for this operation";

}
