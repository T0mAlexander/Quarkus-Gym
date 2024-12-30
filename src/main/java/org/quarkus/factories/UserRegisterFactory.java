package org.quarkus.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.database.UsersRegisterTransaction;
import org.quarkus.services.UserService;

@ApplicationScoped
@SuppressWarnings("unused")
public class UserRegisterFactory {
  public UserService service(UsersRegisterTransaction transaction) {
    return new UserService(transaction);
  }
}