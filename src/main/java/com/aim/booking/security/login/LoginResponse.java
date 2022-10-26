package com.aim.booking.security.login;

import com.aim.booking.security.SystemRolesEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(name = "LoginResponse", description = "Describes the response to an authorization request")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class LoginResponse {

  @Schema(description = "Auth token", example = "eyJzdWIiOiJpdm0uYWRtaW5AdHJhbnNwZXJmZWN0LmNvbSIsImV4cCI6MTYxNDM4NTY0NSwianRpIjoiMSJ9")
  private String token;

  @Schema(description = "Token type", example = "Bearer ")
  @Builder.Default
  private String type = "Bearer";

  @Schema(description = "User full name", example = "John Doe")
  private String username;

  @Schema(description = "User email", example = "some@email.com")
  private String email;

  @Schema(description = "User role in system.", example = "ROLE_USER", implementation = SystemRolesEnum.class)
  private String role;

  @Schema(description = "Identifier for user. ULID format.", example = "01E1H5K5G7YSQFQ96A28CGHZ9B")
  private String userId;
}