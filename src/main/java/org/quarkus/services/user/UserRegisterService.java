package org.quarkus.services.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.transactions.user.UserRegisterTransaction;
import org.quarkus.models.User;
import org.quarkus.services.errors.UserExistsException;

@ApplicationScoped
public class UserRegisterService {
  private final UserRegisterTransaction database;

  @Inject
  public UserRegisterService(UserRegisterTransaction database) {
    this.database = database;
  }

  @WithTransaction
  public Uni<User> create(String name, String email, String password) {
    String passwordHash = BCrypt.withDefaults().hashToString(6, password.toCharArray());

    return database.findByEmail(email)
      .onItem().ifNotNull().failWith(
        new UserExistsException("Este usuário já está cadastrado!"))
      .onItem().ifNull()
      .switchTo(() -> {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordHash);
        return database.create(newUser);
      });
  }
}