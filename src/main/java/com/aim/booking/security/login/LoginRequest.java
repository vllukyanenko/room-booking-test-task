package com.aim.booking.security.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(name = "LoginRequest",
    description = "Request represents data which required for authorization user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LoginRequest {

  @Schema(description = "User email", example = "example@mail.com", required = true)
  private String login;

  @Schema(description = "User's password", example = "test", required = true)
  private String password;
}

