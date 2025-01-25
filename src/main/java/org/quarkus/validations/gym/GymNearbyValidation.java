package org.quarkus.validations.gym;

import io.smallrye.common.constraint.NotNull;

public record GymNearbyValidation(
  @NotNull String name,
  @NotNull String phone,
  @NotNull String distance
) {}