package com.aim.booking.domain.mapper;

import com.aim.booking.domain.BaseDto;
import com.aim.booking.persistence.entity.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@MapperConfig
public interface BaseMappings {

  @Mappings({
      @Mapping(target = "created", source = "created", ignore = true),
      @Mapping(target = "creator", source = "creator", ignore = true),
      @Mapping(target = "id", source = "id", ignore = true)
  })
  BaseEntity anyDtoToEntity(BaseDto entity);
}