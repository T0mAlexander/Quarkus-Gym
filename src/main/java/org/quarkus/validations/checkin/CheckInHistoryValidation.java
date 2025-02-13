package org.quarkus.validations.checkin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Histórico de Check-In.
 * <p>
 * Esta classe define os parâmetros de validação para o histórico de check-ins,
 * incluindo o nome da academia, latitude, longitude, data do check-in e data da validação.
 * </p>
 */

@Schema(name = "Histórico de Check-In")
public record CheckInHistoryValidation(
  @NotNull
  @Schema(description = "Nome da academia")
  String name,

  @NotNull
  @Schema(description = "Latitude do check-in")
  double latitude,

  @NotNull
  @Schema(description = "Longitude do check-in")
  double longitude,

  @NotNull
  @JsonProperty("date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd'/'MM'/'yyyy 'às' HH:mm'h'", locale = "pt-BR")
  @Schema(description = "Data do check-in")
  LocalDateTime date,

  @JsonProperty("validation")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd'/'MM'/'yyyy 'às' HH:mm'h'", locale = "pt-BR")
  @Schema(description = "Data da validação do check-in")
  LocalDateTime validationDate
) {}