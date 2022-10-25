package com.aim.booking.web.swagger;

import com.aim.booking.domain.BookingDto;
import com.aim.booking.web.BookingErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Booking management",
    description = "Operations for booking entity")
public interface BookingControllerEndpoint {

  @Operation(summary = "Create booking", description = "Operation to creating booking.")
  @ApiResponse(responseCode = "201", description = "Booking was created successfully", content = @Content(schema = @Schema(implementation = BookingDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<BookingDto> save(
      @RequestBody(description = "BookingDto instance parameters", required = true, content = @Content(schema = @Schema(implementation = BookingDto.class))) BookingDto bookingDto);

  @Operation(summary = "Update booking", description = "Operation to updating booking.")
  @ApiResponse(responseCode = "202", description = "Booking was updated successfully", content = @Content(schema = @Schema(implementation = BookingDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<BookingDto> update(
      @RequestBody(description = "BookingDto instance parameters", required = true, content = @Content(schema = @Schema(implementation = BookingDto.class))) BookingDto bookingDto,
      @Parameter(in = ParameterIn.PATH, description = "Identifier for booking. ULID format", required = true, style = ParameterStyle.SIMPLE) String bookingId);

  @Operation(summary = "Get list of all bookings", description = "Return all existing bookings.")
  @ApiResponse(responseCode = "200", description = "Successful retrieval list of bookings")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<List<BookingDto>> getAll();

  @Operation(summary = "Get booking", description = "Get booking by id.")
  @ApiResponse(responseCode = "200", description = "Successful retrieval booking", content = @Content(schema = @Schema(implementation = BookingDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<BookingDto> getById(
      @Parameter(in = ParameterIn.PATH, description = "Identifier for booking. ULID format", required = true, style = ParameterStyle.SIMPLE) String bookingId);

  @Operation(summary = "Get booking", description = "Get booking by id.")
  @ApiResponse(responseCode = "200", description = "Successful retrieval booking", content = @Content(schema = @Schema(implementation = BookingDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<List<BookingDto>> getBookingByTimeBoundaries(
      @Parameter(in = ParameterIn.PATH, description = "Identifier for booking. ULID format", required = true, style = ParameterStyle.SIMPLE) OffsetDateTime checkIn,
      @Parameter(in = ParameterIn.PATH, description = "Identifier for booking. ULID format", required = true, style = ParameterStyle.SIMPLE) OffsetDateTime checkIn2);

  @Operation(summary = "Delete booking", description = "Delete booking by id.")
  @ApiResponse(responseCode = "204", description = "Successful deleted booking")
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<Void> deleteById(
      @Parameter(in = ParameterIn.PATH, description = "Identifier for booking. ULID format", required = true, style = ParameterStyle.SIMPLE) String bookingId);
}
