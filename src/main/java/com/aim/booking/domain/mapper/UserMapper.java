package com.aim.booking.domain.mapper;

import com.aim.booking.domain.UserDto;
import com.aim.booking.persistence.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = BaseMappings.class,
    componentModel = "spring"
)
public interface UserMapper {

  UserDto toUserDto(User user);

  User toUser(UserDto userDto);

  @InheritConfiguration(name = "anyDtoToEntity")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
      nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
  @Mapping(target = "userStatus", ignore = true)
  void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
