package com.aim.booking.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Parent class for all project DTOs. Provides all auto-generated fields which can't be set by the
 * user
 * <p>Important: All entities which inherit from this class must be annotated with {@code
 * lombok.experimental.SuperBuilder} (the same as this class), since mappers we use for converting
 * from repository entity to DTO will not set it. Your DTO inherited from this class CAN'T be
 * annotated just with @Builder since builder for this class will be hidden and not called, But
 * fields provided by this class is important for system usage and can't be simply ignored(it will
 * be ignored by mappers)
 */

@Schema(name = "Base", description = "Base dto model. Describes autogenerated parameters. Parent model for all DTO")
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto implements Serializable {

  public static final String OFFSET_DATE_TIME_FORMAT_STR_UTC_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String UTC_TIMEZONE_STR = "UTC";

  @Schema(description = "Identifier for entity. ULID format. Automatically generated by system", format = "uuid", example = "01E1H5K5G7YSQFQ96A28CGHZ9B")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String id;

  @Schema(description = "Date when added/created", readOnly = true, example = "2019-08-22T06:45:23.558+03:00")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private OffsetDateTime created;

  @Schema(description = "User who created", example = "System Administrator")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String creator;

}
