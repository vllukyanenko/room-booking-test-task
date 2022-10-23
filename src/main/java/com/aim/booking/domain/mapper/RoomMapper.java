package com.aim.booking.domain.mapper;

import com.aim.booking.domain.RoomDto;
import com.aim.booking.persistence.entity.Room;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = BaseMappings.class,
    componentModel = "spring"
)
public interface RoomMapper {

  RoomDto toRoomDto(Room room);

  Room toRoom(RoomDto roomDto);

  @InheritConfiguration(name = "anyDtoToEntity")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
      nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
  void updateRoomFromDto(RoomDto roomDto, @MappingTarget Room Room);
}
