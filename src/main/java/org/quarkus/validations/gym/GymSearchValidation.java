package org.quarkus.validations.gym;

import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Busca global de academias.
 * <p>
 * Esta classe define os parâmetros de validação para a busca global de academias,
 * incluindo o nome e telefone da academia.
 * </p>
 */

@Schema(name = "Busca global de academias")
public record GymSearchValidation(
  @NotNull
  @Schema(description = "Nome da academia")
  String name,

  @NotNull
  @Schema(description = "Telefone da academia")
  String phone
) {}