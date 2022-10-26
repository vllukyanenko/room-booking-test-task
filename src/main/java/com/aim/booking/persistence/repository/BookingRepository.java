package com.aim.booking.persistence.repository;

import com.aim.booking.persistence.entity.Booking;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {

  List<Booking> findBookingByRoomId(String roomId);

  List<Booking> getBookingByCheckInAfterAndCheckInIsBefore(OffsetDateTime checkInTime,
      OffsetDateTime checkInTime2);

  boolean existsByIdAndCreator(String id, String creator);
}
