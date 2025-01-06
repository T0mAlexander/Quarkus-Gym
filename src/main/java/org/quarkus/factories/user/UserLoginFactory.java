package org.quarkus.factories.user;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.transactions.user.UserLoginTransaction;
import org.quarkus.services.user.UserLoginService;

@ApplicationScoped
@SuppressWarnings("unused")
public class UserLoginFactory {
  public UserLoginService service(UserLoginTransaction transaction) {
    return new UserLoginService(transaction);
  }
}