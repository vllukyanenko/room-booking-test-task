package com.aim.booking.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(name = "Booking", description = "Describes booking parameters")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingDto extends BaseDto {

  @Schema(description = "Time from which room is booked", example = "13:45:30.123456789+02:00")
  @FutureOrPresent
  private OffsetDateTime checkIn;

  @Schema(description = "Time to which room is booked", example = "13:45:30.123456789+02:00")
  @Future
  private OffsetDateTime checkOut;

  @Schema(description = "Room id", example = "01E1H5K5G7YSQFQ96A28CGHZ9B")
  @NotBlank(message = "{booking.roomId.notblank}")
  private String roomId;
}
