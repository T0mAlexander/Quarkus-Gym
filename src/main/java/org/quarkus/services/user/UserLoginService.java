package org.quarkus.services.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.User;
import org.quarkus.services.errors.InvalidCredentialsException;
import org.quarkus.services.errors.NewSessionException;
import org.quarkus.transactions.UserTransactions;

/**
 * Serviço de login de usuários.
 * <p>
 * Esta classe define os métodos para autenticar usuários na aplicação,
 * incluindo a verificação de credenciais e a criação de sessão.
 * </p>
 */

@ApplicationScoped
public class UserLoginService {
  private final UserTransactions service;

  @Inject
  public UserLoginService(UserTransactions service) {
    this.service = service;
  }

  /**
   * Autentica um usuário com base no email e senha fornecidos.
   *
   * @param email Email do usuário.
   * @param password Senha do usuário.
   * @param token Token de autenticação do usuário.
   * @return O usuário autenticado.
   * @throws InvalidCredentialsException Se as credenciais forem inválidas.
   * @throws NewSessionException Se o usuário já estiver autenticado.
   */

  public Uni<User> auth(String email, String password, String token) {
    if (token != null && !token.isEmpty()) {
      return Uni.createFrom().failure(
        new NewSessionException("Você já está autenticado na aplicação!")
      );
    }

    return service.findByEmail(email)
      .onItem()
      .transformToUni(user -> {
        if (user == null) {
          return Uni.createFrom().failure(
            new InvalidCredentialsException("Usuário inexistente ou credenciais inválidas!")
          );
        }

        boolean passwordMatch = BCrypt.verifyer()
          .verify(password.toCharArray(), user.getPassword()).verified;

        if (!passwordMatch) {
          return Uni.createFrom().failure(
            new InvalidCredentialsException("Senha inválida!")
          );
        }

        return service.create(user);
      });
  }
}