package com.aim.booking.web.controller;

import com.aim.booking.domain.RoomDto;
import com.aim.booking.service.RoomService;
import com.aim.booking.web.swagger.RoomControllerEndpoint;
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
@RequestMapping(path = "/api/rooms/")
public class RoomController implements RoomControllerEndpoint {

  private final RoomService roomService;


  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @PreAuthorize("@securityServiceImpl.isHaveUpdatePermissions()")
  @PostMapping
  public ResponseEntity<RoomDto> save(@RequestBody @Validated RoomDto roomDto) {
    log.debug("Save new room");
    roomDto = roomService.save(roomDto);
    return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
  }

  @PreAuthorize("@securityServiceImpl.isHaveUpdatePermissions()")
  @PutMapping(path = "{roomId}/")
  public ResponseEntity<RoomDto> update(@RequestBody @Validated RoomDto roomDto,
      @PathVariable String roomId) {
    log.debug("Update room with id {}", roomId);
    roomDto = roomService.update(roomDto, roomId);
    return new ResponseEntity<>(roomDto, HttpStatus.ACCEPTED);
  }

  @GetMapping
  public ResponseEntity<List<RoomDto>> getAll() {
    log.debug("Get all rooms");
    List<RoomDto> roomDtoList = roomService.getAll();
    return new ResponseEntity<>(roomDtoList, HttpStatus.OK);
  }

  @GetMapping(path = "{roomId}/")
  public ResponseEntity<RoomDto> getById(@PathVariable String roomId) {
    log.debug("Get room by id {}", roomId);
    RoomDto roomDto = roomService.findById(roomId);
    return new ResponseEntity<>(roomDto, HttpStatus.OK);
  }

  @DeleteMapping(path = "{roomId}/")
  public ResponseEntity<Void> deleteById(@PathVariable String roomId) {
    log.debug("Delete room by id {}", roomId);
    roomService.delete(roomId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
