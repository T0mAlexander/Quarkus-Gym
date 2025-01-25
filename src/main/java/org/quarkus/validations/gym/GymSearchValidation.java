package org.quarkus.validations.gym;

import jakarta.validation.constraints.NotNull;

public record GymSearchValidation(
  @NotNull String name,
  @NotNull String phone
) {}