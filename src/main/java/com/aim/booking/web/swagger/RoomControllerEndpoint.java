package com.aim.booking.web.swagger;

import com.aim.booking.domain.RoomDto;
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
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Room management",
    description = "Operations room entity")
public interface RoomControllerEndpoint {

  @Operation(summary = "Create room", description = "Operation to creating room.")
  @ApiResponse(responseCode = "201", description = "Room was created successfully", content = @Content(schema = @Schema(implementation = RoomDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<RoomDto> save(
      @RequestBody(description = "RoomDto instance parameters", required = true, content = @Content(schema = @Schema(implementation = RoomDto.class))) RoomDto roomDto);

  @Operation(summary = "Update room", description = "Operation to updating room.")
  @ApiResponse(responseCode = "202", description = "Room was updated successfully", content = @Content(schema = @Schema(implementation = RoomDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<RoomDto> update(
      @RequestBody(description = "RoomDto instance parameters", required = true, content = @Content(schema = @Schema(implementation = RoomDto.class))) RoomDto roomDto,
      @Parameter(in = ParameterIn.PATH, description = "Identifier for room. ULID format", required = true, style = ParameterStyle.SIMPLE) String roomId);

  @Operation(summary = "Get list of all rooms", description = "Return all existing rooms.")
  @ApiResponse(responseCode = "200", description = "Successful retrieval list of rooms")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<List<RoomDto>> getAll();

  @Operation(summary = "Get room", description = "Get room by id.")
  @ApiResponse(responseCode = "200", description = "Successful retrieval room", content = @Content(schema = @Schema(implementation = RoomDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<RoomDto> getById(
      @Parameter(in = ParameterIn.PATH, description = "Identifier for room. ULID format", required = true, style = ParameterStyle.SIMPLE) String roomId);

  @Operation(summary = "Delete room", description = "Delete room by id.")
  @ApiResponse(responseCode = "204", description = "Successful deleted room")
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = BookingErrorResponse.class)))
  ResponseEntity<Void> deleteById(
      @Parameter(in = ParameterIn.PATH, description = "Identifier for room. ULID format", required = true, style = ParameterStyle.SIMPLE) String roomId);
}
