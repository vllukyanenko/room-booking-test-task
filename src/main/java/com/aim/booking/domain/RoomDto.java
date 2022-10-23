package com.aim.booking.domain;

import com.aim.booking.persistence.entity.Booking;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(name = "Room", description = "Describes room parameters")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"bookingList"})
public class RoomDto extends BaseDto {

  @Schema(description = "Room name", example = "Conference room №1")
  @Size(max = 100, message = "{room.name.maxsize")
  private String name;

  @Schema(description = "Amount of seats", example = "15")
  private int seatsAmount;

  @Schema(description = "Number of floor", example = "5")
  @Digits(integer = 100, fraction = -5, message = "room.floor.number")
  private String floor;

  @Schema(description = "Description", example = "Some description")
  @Size(max = 1024, message = "{room.description.size}")
  private String description;

  @ArraySchema(arraySchema = @Schema(description = "Session's video records metadata", implementation = RoomDto.class))
  private List<Booking> bookingList;
}
