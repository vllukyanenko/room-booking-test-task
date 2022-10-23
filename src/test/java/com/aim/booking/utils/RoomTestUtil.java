package com.aim.booking.utils;

import com.aim.booking.domain.RoomDto;
import com.aim.booking.domain.mapper.RoomMapper;
import com.aim.booking.persistence.entity.Room;
import com.aim.booking.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomTestUtil {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;

  public RoomTestUtil(RoomRepository roomRepository,
      RoomMapper roomMapper) {
    this.roomRepository = roomRepository;
    this.roomMapper = roomMapper;
  }

  public RoomDto buildRoomDto(String roomName) {
    return RoomDto.builder().floor("2B").name(roomName).seatsAmount(10)
        .description("Some description").build();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Room createRoomDbInstance(String roomName) {
    Room room = roomMapper.toRoom(buildRoomDto(roomName));
    return roomRepository.save(room);
  }
}
