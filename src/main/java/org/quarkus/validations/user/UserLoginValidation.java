package org.quarkus.validations.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("unused")
public class UserLoginValidation {
  @Email
  @NotBlank
  private String email;

  @NotBlank
  @Size(min = 6)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }
}
