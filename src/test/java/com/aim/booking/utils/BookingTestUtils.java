package com.aim.booking.utils;

import com.aim.booking.domain.BookingDto;
import com.aim.booking.domain.mapper.BookingMapper;
import com.aim.booking.persistence.entity.Booking;
import com.aim.booking.persistence.repository.BookingRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingTestUtils {

  private final BookingMapper bookingMapper;
  private final BookingRepository bookingRepository;

  public BookingTestUtils(BookingMapper bookingMapper,
      BookingRepository bookingRepository) {
    this.bookingMapper = bookingMapper;
    this.bookingRepository = bookingRepository;
  }

  public BookingDto buildBookingDto(String roomId, OffsetDateTime checkIn,
      OffsetDateTime checkOut) {
    return BookingDto.builder()
        .roomId(roomId)
        .checkIn(checkIn)
        .checkOut(checkOut)
        .build();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Booking createBookingDbInstance(String roomId, OffsetDateTime checkIn,
      OffsetDateTime checkOut) {
    Booking booking = bookingMapper.toBooking(buildBookingDto(roomId, checkIn, checkOut));
    return bookingRepository.save(booking);
  }
}
