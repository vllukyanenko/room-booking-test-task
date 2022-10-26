package com.aim.booking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aim.booking.annotation.BookingIntegrationTest;
import com.aim.booking.domain.BookingDto;
import com.aim.booking.domain.RoomDto;
import com.aim.booking.persistence.entity.Booking;
import com.aim.booking.persistence.entity.Room;
import com.aim.booking.service.exception.ErrorMessages;
import com.aim.booking.utils.BookingTestUtils;
import com.aim.booking.utils.RoomTestUtil;
import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@BookingIntegrationTest
public class BookingServiceTest {

  @Autowired
  private BookingService bookingService;
  @Autowired
  private BookingTestUtils bookingTestUtils;
  @Autowired
  private RoomTestUtil roomTestUtil;

  private final String roomName = "Test room";


  @Test
  public void when_create_Booking_then_ReturnBookingDto() {
    Room room = roomTestUtil.createRoomDbInstance(roomName);

    BookingDto bookingDto = bookingTestUtils.buildBookingDto(room.getId(), OffsetDateTime.now(),
        OffsetDateTime.now().plusHours(1));
    BookingDto savedBookingDto = bookingService.save(bookingDto);

    assertThat(savedBookingDto.getRoomId()).isEqualTo(room.getId());
    assertThat(savedBookingDto.getCheckIn()).isEqualTo(bookingDto.getCheckIn());
    assertThat(savedBookingDto.getCheckOut()).isEqualTo(bookingDto.getCheckOut());
  }

  @Test
  public void whenUpdateBooking_thenReturnUpdatedBookingDto() {
    Room room = roomTestUtil.createRoomDbInstance(roomName);

    Booking savedBooking = bookingTestUtils.createBookingDbInstance(room.getId(),
        OffsetDateTime.now(),
        OffsetDateTime.now().plusHours(1));

    OffsetDateTime updatedCheckIn = OffsetDateTime.now().plusHours(1);
    OffsetDateTime updatedCheckOut = OffsetDateTime.now().plusHours(2);

    assertThat(updatedCheckIn).isNotEqualTo(savedBooking.getCheckIn());
    assertThat(updatedCheckOut).isNotEqualTo(savedBooking.getCheckOut());

    BookingDto bookingDto = bookingService.findById(savedBooking.getId());
    bookingDto.setCheckIn(updatedCheckIn);
    bookingDto.setCheckOut(updatedCheckOut);

    BookingDto result = bookingService.update(bookingDto, savedBooking.getId());
    assertThat(result.getId()).isEqualTo(savedBooking.getId());
    assertThat(result.getCheckIn()).isEqualTo(updatedCheckIn);
    assertThat(result.getCheckOut()).isEqualTo(updatedCheckOut);
  }

  @Test
  public void when_get_usersList_then_ReturnBookingDtoList() {
    int bookingAmount = 5;
    Room room = roomTestUtil.createRoomDbInstance(roomName);

    for (int i = 0; i < bookingAmount; i++) {
      bookingTestUtils.createBookingDbInstance(room.getId(), OffsetDateTime.now(),
          OffsetDateTime.now().plusHours(1));
    }
    List<BookingDto> savedBookingDtoList = bookingService.getAll();
    assertThat(savedBookingDtoList.size()).isEqualTo(bookingAmount);
  }

  @Test
  public void when_findBookingById_then_BookingDto() {
    Room room = roomTestUtil.createRoomDbInstance(roomName);
    Booking savedBooking = bookingTestUtils.createBookingDbInstance(room.getId(),
        OffsetDateTime.now(),
        OffsetDateTime.now().plusHours(1));
    BookingDto fetchedBookingDto = bookingService.findById(savedBooking.getId());
    assertThat(fetchedBookingDto.getId()).isEqualTo(savedBooking.getId());
  }

  @Test
  public void when_deleteBookingById_then_BookingShouldBeDeleted() {
    Room room = roomTestUtil.createRoomDbInstance(roomName);
    Booking savedBooking = bookingTestUtils.createBookingDbInstance(room.getId(),
        OffsetDateTime.now(),
        OffsetDateTime.now().plusHours(1));
    bookingService.delete(savedBooking.getId());

    Exception exception = assertThrows(EntityNotFoundException.class, () -> {
      bookingService.findById(savedBooking.getId());
    });
    assertTrue(exception.getMessage()
        .contains(
            String.format(ErrorMessages.BOOKING_WITH_ID_DOES_NOT_EXIST, savedBooking.getId())));
  }

  @Test
  public void when_getBookingByTimeBoundaries_then_return_bookingDtoList() {
    int bookingAmount = 5;
    System.out.println(OffsetDateTime.now());
    Room room = roomTestUtil.createRoomDbInstance(roomName);
    for (int i = 0; i < bookingAmount; i++) {
      bookingTestUtils.createBookingDbInstance(room.getId(), OffsetDateTime.now().plusDays(i),
          OffsetDateTime.now().plusDays(i).plusHours(1));
    }

    List<BookingDto> bookingDtoList = bookingService.getBookingByTimeBoundaries(
        OffsetDateTime.now().minusHours(1), OffsetDateTime.now().plusDays(5));

    assertThat(bookingDtoList.size()).isEqualTo(bookingAmount);
  }


}
