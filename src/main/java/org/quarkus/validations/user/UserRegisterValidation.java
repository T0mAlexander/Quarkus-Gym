package org.quarkus.validations.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.quarkus.utils.user.Role;

/**
 * Registro de usuário.
 * <p>
 * Esta classe define os parâmetros de validação para o registro de um novo usuário,
 * incluindo o nome, e-mail, senha e cargo do novo usuário.
 * </p>
 */

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
  @Schema(description = "Senha do novo usuário")
  String password,

  @NotNull
  @Schema(description = "Cargo do novo usuário")
  Role role
) {}