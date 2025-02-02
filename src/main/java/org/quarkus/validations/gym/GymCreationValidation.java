package org.quarkus.validations.gym;

import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Registro de academia.
 * <p>
 * Esta classe define os parâmetros de validação para o registro de uma academia,
 * incluindo o nome, e-mail, descrição, telefone, latitude e longitude da academia.
 * </p>
 */

@Schema(name = "Registro de academia")
public record GymCreationValidation(
  @NotNull
  @Schema(description = "Nome da academia")
  String name,

  @NotNull
  @Schema(description = "E-mail da academia")
  String email,

  @Schema(description = "Slogan ou descrição da academia")
  String description,

  @NotNull
  @Schema(description = "Telefone da academia")
  String phone,

  @NotNull
  @Schema(description = "Latitude da academia")
  double latitude,

  @NotNull
  @Schema(description = "Longitude da academia")
  double longitude
) {}