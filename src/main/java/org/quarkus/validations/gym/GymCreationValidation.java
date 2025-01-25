package org.quarkus.validations.gym;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GymCreationValidation(
  @NotEmpty String name,
  @NotEmpty String email,
  String description,
  @NotEmpty String phone,
  @NotNull double latitude,
  @NotNull double longitude
) {}