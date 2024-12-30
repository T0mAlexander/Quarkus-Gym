package org.quarkus.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.database.UserLoginTransaction;
import org.quarkus.services.UserLoginService;

@ApplicationScoped
public class UserLoginFactory {
  public UserLoginService service(UserLoginTransaction transaction) {
    return new UserLoginService(transaction);
  }
}