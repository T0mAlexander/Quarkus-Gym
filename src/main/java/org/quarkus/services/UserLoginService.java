package org.quarkus.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.database.UserLoginTransaction;
import org.quarkus.models.User;
import org.quarkus.services.errors.InvalidCredentialsException;

@ApplicationScoped
public class UserLoginService {
  private final UserLoginTransaction database;

  @Inject
  public UserLoginService(UserLoginTransaction database) {
    this.database = database;
  }

  public User auth(String email, String password) {
    User user = database.findUser(email, password);

    if (user == null || !BCrypt.verifyer()
      .verify(
        password.toCharArray(),
        user.getPassword()
      ).verified) {
      throw new InvalidCredentialsException("Credenciais inválidas!");
    }

    return user;
  }
}
