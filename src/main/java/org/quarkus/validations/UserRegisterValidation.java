package org.quarkus.validations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterValidation {
  @NotBlank
  private String name;

  @Email
  @NotBlank
  private String email;

  @NotBlank
  @Size(min = 6)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}