package com.aim.booking.service;

import com.aim.booking.domain.BookingDto;
import java.time.OffsetDateTime;
import java.util.List;

public interface BookingService extends BaseService<BookingDto> {

  List<BookingDto> getBookingByTimeBoundaries(OffsetDateTime checkInTime,
      OffsetDateTime checkInTime2);
}
