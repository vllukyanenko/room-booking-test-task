package com.aim.booking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aim.booking.annotation.BookingIntegrationTest;
import com.aim.booking.domain.RoomDto;
import com.aim.booking.persistence.entity.Room;
import com.aim.booking.service.exception.ErrorMessages;
import com.aim.booking.utils.RoomTestUtil;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@BookingIntegrationTest
public class RoomServiceTest {

  @Autowired
  private RoomService roomService;
  @Autowired
  private RoomTestUtil roomTestUtil;

  private final String roomName = "Test room";

  @Test
  public void when_create_Room_then_ReturnRoomDto() {
    RoomDto roomDto = roomTestUtil.buildRoomDto(roomName);
    RoomDto savedRoomDto = roomService.save(roomDto);
    assertThat(savedRoomDto.getName()).isEqualTo(roomDto.getName());
  }

  @Test
  public void whenUpdateRoom_thenReturnUpdatedRoomDto() {
    Room savedRoom = roomTestUtil.createRoomDbInstance(roomName);
    String roomName = "newFirst";
    String floor = "1A";
    String description = "Updated description";

    assertThat(roomName).isNotEqualTo(savedRoom.getName());
    assertThat(floor).isNotEqualTo(savedRoom.getFloor());
    assertThat(description).isNotEqualTo(savedRoom.getDescription());

    RoomDto roomDto = roomService.findById(savedRoom.getId());
    roomDto.setName(roomName);
    roomDto.setFloor(floor);
    roomDto.setDescription(description);

    RoomDto result = roomService.update(roomDto, savedRoom.getId());
    assertThat(result.getId()).isEqualTo(savedRoom.getId());
    assertThat(result.getName()).isEqualTo(roomName);
    assertThat(result.getFloor()).isEqualTo(floor);
    assertThat(result.getDescription()).isEqualTo(description);
  }

  @Test
  public void when_get_RoomList_then_ReturnRoomDtoList() {
    int roomAmount = 5;
    for (int i = 0; i < roomAmount; i++) {
      roomTestUtil.createRoomDbInstance(StringUtils.join(roomName, String.valueOf(i)));
    }
    List<RoomDto> savedRoomDtoList = roomService.getAll();
    assertThat(savedRoomDtoList.size()).isEqualTo(roomAmount);
  }

  @Test
  public void when_findById_then_RoomDto() {
    Room savedRoom = roomTestUtil.createRoomDbInstance(roomName);
    RoomDto fetchedRoom = roomService.findById(savedRoom.getId());
    assertThat(fetchedRoom.getId()).isEqualTo(savedRoom.getId());
  }

  @Test
  public void when_deleteRoomById_then_RoomShouldBeDeleted() {
    Room savedRoom = roomTestUtil.createRoomDbInstance(roomName);
    roomService.delete(savedRoom.getId());

    Exception exception = assertThrows(EntityNotFoundException.class, () -> {
      roomService.findById(savedRoom.getId());
    });
    assertTrue(exception.getMessage()
        .contains(String.format(ErrorMessages.ROOM_WITH_ID_DOES_NOT_EXIST, savedRoom.getId())));
  }

}
