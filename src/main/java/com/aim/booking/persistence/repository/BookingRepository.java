package com.aim.booking.persistence.repository;

import com.aim.booking.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,String> {

}
