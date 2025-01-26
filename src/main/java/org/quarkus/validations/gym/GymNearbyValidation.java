package org.quarkus.validations.gym;

import io.smallrye.common.constraint.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
  name = "Busca de academias próximas",
  description = "Raio máximo de busca: 5 km"
)
public record GymNearbyValidation(
  @NotNull
  @Schema(description = "Nome da academia")
  String name,

  @NotNull
  @Schema(description = "Telefone da academia")
  String phone,

  @NotNull
  @Schema(description = "Distância do usuário até a academia")
  String distance
) {}