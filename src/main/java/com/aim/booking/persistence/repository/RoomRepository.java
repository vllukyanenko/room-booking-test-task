package com.aim.booking.persistence.repository;

import com.aim.booking.persistence.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {

}
