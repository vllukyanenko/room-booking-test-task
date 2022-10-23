package com.aim.booking.domain;

import com.aim.booking.persistence.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(name = "User", description = "Describes user parameters")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {

  @Schema(description = "Firs name", example = "Tomas")
  @NotBlank(message = "{user.lastname.notblank}")
  @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "{user.lastname.invalid}")
  @Size(max = 80, message = "{user.firstname.maxsize}")
  private String firstName;
  @Schema(description = "Firs name", example = "Shelby")
  @NotBlank(message = "{user.lastname.notblank}")
  @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "{user.lastname.invalid}")
  @Size(max = 80, message = "{user.lastname.maxsize}")
  private String lastName;

  @Schema(description = "User email", example = "some@email.com", required = true)
  @NotBlank(message = "{user.email.notblank}")
  @Email(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "{user.email.invalid}")
  @Size(max = 80, message = "{user.email.maxsize}")
  private String email;

  @Schema(description = "User password", example = "Qwerty_!23", required = true)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank(message = "{user.password.notblank}")
  private String password;

  @Schema(description = "User profile status", example = "ACTIVE/INACTIVE", required = true)
  private UserStatus userStatus;

}
