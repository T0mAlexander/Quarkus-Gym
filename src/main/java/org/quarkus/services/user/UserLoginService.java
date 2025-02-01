package org.quarkus.services.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.User;
import org.quarkus.services.errors.InvalidCredentialsException;
import org.quarkus.services.errors.NewSessionException;
import org.quarkus.transactions.UserTransactions;

@ApplicationScoped
public class UserLoginService {
  private final UserTransactions service;

  @Inject
  public UserLoginService(UserTransactions service) {
    this.service = service;
  }

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