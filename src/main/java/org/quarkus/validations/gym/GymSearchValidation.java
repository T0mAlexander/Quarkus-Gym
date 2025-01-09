package org.quarkus.validations.gym;

import jakarta.validation.constraints.NotNull;

public class GymSearchValidation {
  @NotNull String name;
  @NotNull String phone;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
