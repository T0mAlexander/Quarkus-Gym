package org.quarkus.services.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.User;
import org.quarkus.services.errors.UserExistsException;
import org.quarkus.transactions.UserTransactions;
import org.quarkus.utils.user.Role;

import java.util.UUID;

/**
 * Serviço de registro de usuários.
 * <p>
 * Esta classe define os métodos para registrar novos usuários na aplicação,
 * incluindo a verificação de existência de usuário e a criação de um novo usuário.
 * </p>
 */

@ApplicationScoped
public class UserRegisterService {
  private final UserTransactions service;

  @Inject
  public UserRegisterService(UserTransactions service) {
    this.service = service;
  }

  /**
   * Cria um novo usuário.
   *
   * @param name Nome do usuário.
   * @param email Email do usuário.
   * @param password Senha do usuário.
   * @param role Cargo do usuário.
   * @return O usuário criado.
   * @throws UserExistsException Se o usuário já estiver cadastrado.
   */
  @WithTransaction
  public Uni<User> create(String name, String email, String password, Role role) {
    String passwordHash = BCrypt.withDefaults().hashToString(6, password.toCharArray());

    return service.findByEmail(email)
      .onItem().ifNotNull().failWith(
        new UserExistsException("Este usuário já está cadastrado!"))
      .onItem().ifNull()
      .switchTo(() -> {
          UUID userId = UUID.randomUUID();
          User newUser = new User(userId);

          newUser.setName(name);
          newUser.setEmail(email);
          newUser.setPassword(passwordHash);
          newUser.setRole(role);

          return service.create(newUser);
        }
      );
  }
}