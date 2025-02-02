package org.quarkus.validations.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Login de usuário.
 * <p>
 * Esta classe define os parâmetros de validação para o login de um usuário,
 * incluindo o e-mail e a senha do usuário cadastrado.
 * </p>
 */

@Schema(name = "Login de usuário")
@SuppressWarnings("unused")
public record UserLoginValidation (
  @Email
  @NotNull
  @Schema(description = "E-mail do usuário cadastrado")
  String email,

  @NotNull
  @Size(min = 6)
  @Schema(description = "Senha do usuário cadastrado")
  String password
) {}