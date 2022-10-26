package com.aim.booking.service.impl;

import com.aim.booking.config.BookingTimeConfiguration;
import com.aim.booking.domain.BookingDto;
import com.aim.booking.domain.mapper.BookingMapper;
import com.aim.booking.persistence.entity.Booking;
import com.aim.booking.persistence.repository.BookingRepository;
import com.aim.booking.service.BookingService;
import com.aim.booking.service.exception.ErrorMessages;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@EnableConfigurationProperties(BookingTimeConfiguration.class)
public class BookingServiceImpl implements BookingService {

  private final BookingMapper bookingMapper;
  private final BookingRepository bookingRepository;
  private final BookingTimeConfiguration bookingTimeConfiguration;

  public BookingServiceImpl(BookingMapper bookingMapper,
      BookingRepository bookingRepository,
      BookingTimeConfiguration bookingTimeConfiguration) {
    this.bookingMapper = bookingMapper;
    this.bookingRepository = bookingRepository;
    this.bookingTimeConfiguration = bookingTimeConfiguration;
  }

  @Override
  public BookingDto save(BookingDto dto) {
    log.debug("Save booking");
    validateBookingOverlappingAndBoundaries(dto);
    Booking booking = bookingMapper.toBooking(dto);
    booking = bookingRepository.save(booking);
    return bookingMapper.toBookingDto(booking);
  }

  @Override
  public BookingDto update(BookingDto dto, String id) {
    log.debug("Update booking bi {}", id);
    validateBookingOverlappingAndBoundaries(dto);
    Booking booking = bookingRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(
            String.format(ErrorMessages.BOOKING_WITH_ID_DOES_NOT_EXIST, id))
    );
    bookingMapper.updateBookingFromDto(dto, booking);
    booking = bookingRepository.save(booking);
    return bookingMapper.toBookingDto(booking);
  }

  @Override
  @Transactional(readOnly = true)
  public BookingDto findById(String id) {
    log.debug("Find booking by id {}", id);
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format(
            ErrorMessages.BOOKING_WITH_ID_DOES_NOT_EXIST, id)));
    return bookingMapper.toBookingDto(booking);
  }

  @Override
  @Transactional(readOnly = true)
  public List<BookingDto> getAll() {
    log.debug("Find all bookings");
    List<Booking> bookingList = bookingRepository.findAll();
    return bookingList.stream().map(
            bookingMapper::toBookingDto)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(String id) {
    log.debug("Delete booking by id {}", id);
    if (!bookingRepository.existsById(id)) {
      throw new EntityNotFoundException(
          String.format(ErrorMessages.ROOM_WITH_ID_DOES_NOT_EXIST, id));
    }
    bookingRepository.deleteById(id);
  }

  private void validateBookingOverlappingAndBoundaries(BookingDto bookingDto) {
    log.debug("Validate booking overlapping and boundaries");
    int finalBookingDays = bookingTimeConfiguration.getFinalAvailableBookingWeekDayNumber();

    if (Duration.between(bookingDto.getCheckIn(), bookingDto.getCheckOut()).toMinutes()
        < bookingTimeConfiguration.getMinimalBookingTime()) {
      throw new DateTimeException(String.format(ErrorMessages.MINIMAL_BOOKING_TIME_OF_ROOM_IS,
          bookingTimeConfiguration.getMinimalBookingTime()));
    }

    if (bookingDto.getCheckIn().getDayOfWeek().getValue()
        > finalBookingDays
        || bookingDto.getCheckOut().getDayOfWeek().getValue()
        > finalBookingDays) {
      throw new DateTimeException(ErrorMessages.BOOKING_OF_ROOM_IS_POSSIBLE_FROM_MN_TO_FR);
    }
    int initialBookingTime = bookingTimeConfiguration.getInitialAvailableBookingHours();
    int finalBookingTime = bookingTimeConfiguration.getFinalAvailableBookingHour();
    if (bookingDto.getCheckIn().getHour() > finalBookingTime
        || bookingDto.getCheckIn().getHour() < initialBookingTime
        || bookingDto.getCheckOut().getHour() > finalBookingTime
        || bookingDto.getCheckOut().getHour() < initialBookingTime) {
      throw new DateTimeException(ErrorMessages.BOOKING_OF_ROOM_IS_POSSIBLE_FROM_9_TO_17);
    }
    List<Booking> bookingList = bookingRepository.findBookingByRoomId(bookingDto.getRoomId());
    bookingList.forEach(booking -> {
      if (isDateOverlapped(booking.getCheckIn(), booking.getCheckOut(),
          bookingDto.getCheckIn(), bookingDto.getCheckOut())) {
        throw new DateTimeException(ErrorMessages.BOOKING_TIME_ARE_OVERLAPPED);
      }
    });
  }

  private boolean isDateOverlapped(OffsetDateTime startDateTime, OffsetDateTime endDateTime,
      OffsetDateTime checkInTime, OffsetDateTime checkOutTime) {
    return checkInTime.isAfter(startDateTime) && checkInTime.isBefore(endDateTime) || (
        checkOutTime.isAfter(startDateTime) && checkOutTime.isBefore(endDateTime) || (
            checkInTime.isAfter(startDateTime) && checkInTime.isBefore(endDateTime)));
  }

  @Override
  public List<BookingDto> getBookingByTimeBoundaries(OffsetDateTime checkInTimeFrom,
      OffsetDateTime checkInTimeTo) {
    log.debug("Get all bookings which have checkIn after {} and before {}", checkInTimeFrom,
        checkInTimeTo);
    List<Booking> bookingList = bookingRepository.getBookingByCheckInAfterAndCheckInIsBefore(
        checkInTimeFrom, checkInTimeTo);
    return bookingList.stream().map(
            bookingMapper::toBookingDto)
        .collect(Collectors.toList());
  }

  @Override
  public boolean userIsCreator(String bookingId, String userEmail) {
    log.debug("Check booking is exist by id {} and creator {}", bookingId, userEmail);
    return bookingRepository.existsByIdAndCreator(bookingId, userEmail);
  }
}
