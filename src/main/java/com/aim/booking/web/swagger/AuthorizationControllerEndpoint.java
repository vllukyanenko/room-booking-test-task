package com.aim.booking.web.swagger;

import com.aim.booking.security.login.LoginRequest;
import com.aim.booking.security.login.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication management",
    description = "Login and logout endpoints")
public interface AuthorizationControllerEndpoint {

  @Operation(summary = "Login", description = "Return authentication token.")
  @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  ResponseEntity<LoginResponse> login(
      @RequestBody(description = "Login parameters", required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class))) LoginRequest request);


  @Operation(summary = "Logout", description = "Logout user from application.")
  @ApiResponse(responseCode = "204", description = "User has been successfully logout", content = @Content)
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  ResponseEntity<Void> logout();
}