package org.quarkus.services.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.services.errors.NewSessionException;
import org.quarkus.transactions.user.UserLoginTransaction;
import org.quarkus.models.User;
import org.quarkus.services.errors.InvalidCredentialsException;

@ApplicationScoped
public class UserLoginService {
  private final UserLoginTransaction database;

  @Inject
  public UserLoginService(UserLoginTransaction database) {
    this.database = database;
  }

  public Uni<User> auth(String email, String password, String token) {
    if (token != null && !token.isEmpty()) {
      return Uni.createFrom().failure(
        new NewSessionException("Você já está autenticado na aplicação!")
      );
    }

    return database.findByEmail(email)
      .onItem()
      .transform(user -> {
        if (user == null) {
          throw new InvalidCredentialsException("Usuário inexistente ou credenciais inválidas!");
        }

        boolean passwordMatch;
        passwordMatch = BCrypt.verifyer()
          .verify(
            password.toCharArray(),
            user.getPassword()).verified;

        if (!passwordMatch) {
          throw new InvalidCredentialsException("Senha inválida!");
        }

        return user;
      });
  }
}
