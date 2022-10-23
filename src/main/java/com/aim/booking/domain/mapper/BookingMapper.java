package com.aim.booking.domain.mapper;

import com.aim.booking.domain.BookingDto;
import com.aim.booking.persistence.entity.Booking;
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
public interface BookingMapper {

  BookingDto toBookingDto(Booking booking);

  Booking toBooking(BookingDto bookingDto);

  @InheritConfiguration(name = "anyDtoToEntity")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
      nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
  void updateBookingFromDto(BookingDto bookingDto, @MappingTarget Booking booking);
}
