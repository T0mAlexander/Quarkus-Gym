package org.quarkus.validations.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Registro de usuário")
@SuppressWarnings("unused")
public record UserRegisterValidation (
  @NotNull
  @Schema(description = "Nome do novo usuário")
  String name,

  @Email
  @NotNull
  @Schema(description = "E-mail do novo usuário")
  String email,

  @NotNull
  @Size(min = 6)
  @JsonProperty(access = Access.WRITE_ONLY)
  @Schema(description = "Senha do novo usuário")
  String password
) {}