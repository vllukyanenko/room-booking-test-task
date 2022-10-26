package com.aim.booking.web.controller;

import com.aim.booking.domain.BookingDto;
import com.aim.booking.service.BookingService;
import com.aim.booking.web.swagger.BookingControllerEndpoint;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/bookings/")
public class BookingController implements BookingControllerEndpoint {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping
  public ResponseEntity<BookingDto> save(@RequestBody @Validated BookingDto bookingDto) {
    log.debug("Save new booking");
    bookingDto = bookingService.save(bookingDto);
    return new ResponseEntity<>(bookingDto, HttpStatus.CREATED);
  }

  @PreAuthorize("@securityServiceImpl.isCreator(#bookingId)")
  @PutMapping(path = "{bookingId}/")
  public ResponseEntity<BookingDto> update(@RequestBody @Validated BookingDto bookingDto,
      @PathVariable String bookingId) {
    log.debug("Update booking with id {}", bookingDto.getId());
    bookingDto = bookingService.update(bookingDto, bookingId);
    return new ResponseEntity<>(bookingDto, HttpStatus.ACCEPTED);
  }

  @GetMapping
  public ResponseEntity<List<BookingDto>> getAll() {
    log.debug("Get all bookings");
    List<BookingDto> bookingDtoList = bookingService.getAll();
    return new ResponseEntity<>(bookingDtoList, HttpStatus.OK);
  }

  @GetMapping(path = "time/{checkInTimeFrom}/{checkInTimeTo}/")
  public ResponseEntity<List<BookingDto>> getBookingByTimeBoundaries(
      @PathVariable OffsetDateTime checkInTimeFrom, @PathVariable OffsetDateTime checkInTimeTo) {
    log.debug("Get bookings in time boundaries from {} to {}", checkInTimeFrom, checkInTimeTo);
    List<BookingDto> bookingDtoList = bookingService.getBookingByTimeBoundaries(checkInTimeFrom,
        checkInTimeTo);
    return new ResponseEntity<>(bookingDtoList, HttpStatus.OK);
  }

  @GetMapping(path = "{bookingId}/")
  public ResponseEntity<BookingDto> getById(@PathVariable String bookingId) {
    log.debug("Get booking by id {}", bookingId);
    BookingDto bookingDto = bookingService.findById(bookingId);
    return new ResponseEntity<>(bookingDto, HttpStatus.OK);
  }

  @DeleteMapping(path = "{bookingId}/")
  public ResponseEntity<Void> deleteById(@PathVariable String bookingId) {
    log.debug("Delete booking by id {}", bookingId);
    bookingService.delete(bookingId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
