package com.aim.booking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "booking.time")
public class BookingTimeConfiguration {

  private int initialAvailableBookingWeekDayNumber;
  private int finalAvailableBookingWeekDayNumber;
  private int initialAvailableBookingHours;
  private int finalAvailableBookingHour;
  private int minimalBookingTime;

}
