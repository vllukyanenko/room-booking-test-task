package com.aim.booking.service;

import com.aim.booking.domain.BaseDto;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

public interface BaseService<T extends BaseDto> {

  T save(@Valid T dto);

  T update(@Valid T dto, String id);

  T findById(String id);

  List<T> getAll();

  void delete(String id);

}