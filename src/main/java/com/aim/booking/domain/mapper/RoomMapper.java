package com.aim.booking.domain.mapper;

import com.aim.booking.domain.BookingDto;
import com.aim.booking.domain.RoomDto;
import com.aim.booking.persistence.entity.Booking;
import com.aim.booking.persistence.entity.Room;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = BaseMappings.class,
    componentModel = "spring"
)
public abstract class RoomMapper {

  @Autowired
  private BookingMapper bookingMapper;

  @Mapping(target = "bookingList", source = "bookingList", qualifiedByName = "includeBookings")
  public abstract RoomDto toRoomDto(Room room);

  public abstract Room toRoom(RoomDto roomDto);

  @InheritConfiguration(name = "anyDtoToEntity")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
      nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
  public abstract void updateRoomFromDto(RoomDto roomDto, @MappingTarget Room Room);

  @Named("includeBookings")
  protected List<BookingDto> includeBookings(List<Booking> bookingList) {
    if (bookingList == null || bookingList.isEmpty()) {
      return null;
    }
    return bookingList.stream()
        .map(booking -> bookingMapper.toBookingDto(booking))
        .collect(Collectors.toList());
  }
}
