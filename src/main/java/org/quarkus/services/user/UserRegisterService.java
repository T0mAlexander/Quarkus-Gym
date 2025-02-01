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

@ApplicationScoped
public class UserRegisterService {
  private final UserTransactions service;

  @Inject
  public UserRegisterService(UserTransactions service) {
    this.service = service;
  }

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