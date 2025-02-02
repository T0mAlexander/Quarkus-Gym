package org.quarkus.validations.checkin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Validação de Check-In.
 * <p>
 * Esta classe define os parâmetros de validação para a criação de um check-in,
 * incluindo a latitude e longitude atuais do usuário.
 * </p>
 */

@Schema(name = "Validação de Check-In")
public record CheckInCreationValidation(
  @NotNull
  @Min(-90)
  @Max(90)
  @Schema(description = "Latitude atual do usuário")
  Double latitude,

  @NotNull
  @Min(-180)
  @Max(180)
  @Schema(description = "Longitude atual do usuário")
  Double longitude
) {}