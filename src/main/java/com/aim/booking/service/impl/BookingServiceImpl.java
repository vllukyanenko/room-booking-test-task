package com.aim.booking.service.impl;

import com.aim.booking.domain.BookingDto;
import com.aim.booking.domain.mapper.BookingMapper;
import com.aim.booking.persistence.entity.Booking;
import com.aim.booking.persistence.repository.BookingRepository;
import com.aim.booking.service.BookingService;
import com.aim.booking.service.exception.ErrorMessages;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

  private final BookingMapper bookingMapper;
  private final BookingRepository bookingRepository;

  public BookingServiceImpl(BookingMapper bookingMapper,
      BookingRepository bookingRepository) {
    this.bookingMapper = bookingMapper;
    this.bookingRepository = bookingRepository;
  }

  @Override
  public BookingDto save(BookingDto dto) {
    log.debug("Save booking");
    validateBookingOverlapping(dto);
    Booking booking = bookingMapper.toBooking(dto);
    booking = bookingRepository.save(booking);
    return bookingMapper.toBookingDto(booking);
  }

  @Override
  public BookingDto update(BookingDto dto, String id) {
    log.debug("Update booking bi {}", id);
    validateBookingOverlapping(dto);
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

  private void validateBookingOverlapping(BookingDto bookingDto) {
    log.debug("Validate booking overlapping");
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
  public List<BookingDto> getBookingByTimeBoundaries(OffsetDateTime checkInTime,
      OffsetDateTime checkInTime2) {
    log.debug("Get all bookings which have checkIn after {} and before {}", checkInTime,
        checkInTime2);
    List<Booking> bookingList = bookingRepository.getBookingByCheckInAfterAndCheckInIsBefore(
        checkInTime, checkInTime2);
    return bookingList.stream().map(
            bookingMapper::toBookingDto)
        .collect(Collectors.toList());
  }
}
