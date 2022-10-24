package com.aim.booking.service.exception;

public class ErrorMessages {
  //USER
  public static final String USER_WITH_ID_DOES_NOT_EXIST = "User with id %s doesn't exist";
  public static final String USER_STATUS_IS_INCORRECT = "User status %s is incorrect";


 //ROOM
  public static final String ROOM_WITH_ID_DOES_NOT_EXIST = "Room with id %s doesn't exist";


  //BOOKING
  public static final String BOOKING_WITH_ID_DOES_NOT_EXIST = "Booking with id %s doesn't exist";
  public static final String BOOKING_TIME_ARE_OVERLAPPED = "Cannot book rooms. Time of booking are overlapped";
  public static final String BOOKING_OF_ROOM_IS_POSSIBLE_FROM_MN_TO_FR = "Room booking is possible from Monday to Friday";
  public static final String BOOKING_OF_ROOM_IS_POSSIBLE_FROM_9_TO_17 = "Room booking is possible from 9:00 to 17:00";

}
