package com.aim.booking.web;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Describes main errors on REST api`s actions or service")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingErrorResponse implements Serializable {

  @Schema(description = "Detailed server error message", example = "Room with with id 01E1H5K5G7YSQFQ96A28CGHZ9B doesn't exists")
  private String error;

}
