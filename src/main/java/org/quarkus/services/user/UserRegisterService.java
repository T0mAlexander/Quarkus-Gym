package org.quarkus.services.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.transactions.UserTransactions;
import org.quarkus.objects.User;
import org.quarkus.services.errors.UserExistsException;

@ApplicationScoped
public class UserRegisterService {
  private final UserTransactions service;

  @Inject
  public UserRegisterService(UserTransactions service) {
    this.service = service;
  }

  @WithTransaction
  public Uni<User> create(String name, String email, String password) {
    String passwordHash = BCrypt.withDefaults().hashToString(6, password.toCharArray());

    return service.findByEmail(email)
      .onItem().ifNotNull().failWith(
        new UserExistsException("Este usuário já está cadastrado!"))
      .onItem().ifNull()
      .switchTo(() -> {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordHash);
        return service.create(newUser);
      });
  }
}