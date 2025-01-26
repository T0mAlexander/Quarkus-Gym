package org.quarkus.validations.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Login de usuário")
@SuppressWarnings("unused")
public record UserLoginValidation (
  @Email
  @NotNull
  @Schema(description = "E-mail do usuário cadastrado")
  String email,

  @NotNull
  @Size(min = 6)
  @JsonProperty(access = Access.WRITE_ONLY)
  @Schema(description = "Senha do usuário cadastrado")
  String password
) {}