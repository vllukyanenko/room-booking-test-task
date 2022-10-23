package com.aim.booking.service.impl;

import com.aim.booking.domain.RoomDto;
import com.aim.booking.domain.mapper.RoomMapper;
import com.aim.booking.persistence.entity.Room;
import com.aim.booking.persistence.repository.RoomRepository;
import com.aim.booking.service.RoomService;
import com.aim.booking.service.exception.ErrorMessages;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;

  public RoomServiceImpl(RoomRepository roomRepository,
      RoomMapper roomMapper) {
    this.roomRepository = roomRepository;
    this.roomMapper = roomMapper;
  }

  @Override
  public RoomDto save(RoomDto dto) {
    log.debug("Save new room");
    Room room = roomMapper.toRoom(dto);
    room = roomRepository.save(room);
    return roomMapper.toRoomDto(room);
  }

  @Override
  public RoomDto update(RoomDto dto, String id) {
    log.debug("Update room with id {}", id);
    Room room = roomRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(
            String.format(ErrorMessages.ROOM_WITH_ID_DOES_NOT_EXIST, id))
    );
    roomMapper.updateRoomFromDto(dto, room);
    room = roomRepository.save(room);
    return roomMapper.toRoomDto(room);
  }

  @Override
  @Transactional(readOnly = true)
  public RoomDto findById(String id) {
    log.debug("Find room by id {}", id);
    Room room = roomRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format(
            ErrorMessages.ROOM_WITH_ID_DOES_NOT_EXIST, id)));
    return roomMapper.toRoomDto(room);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RoomDto> getAll() {
    log.debug("Get all the rooms");
    List<Room> roomList = roomRepository.findAll();
    return roomList.stream().map(
            roomMapper::toRoomDto)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(String id) {
    log.debug("Delete room by id {}", id);
    if (!roomRepository.existsById(id)) {
      throw new EntityNotFoundException(
          String.format(ErrorMessages.ROOM_WITH_ID_DOES_NOT_EXIST, id));
    }
    roomRepository.deleteById(id);
  }

}
