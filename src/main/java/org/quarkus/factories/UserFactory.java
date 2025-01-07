package org.quarkus.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.user.UserLoginService;
import org.quarkus.transactions.UserTransactions;
import org.quarkus.services.user.UserRegisterService;

@ApplicationScoped
@SuppressWarnings("unused")
public class UserFactory {
  public UserRegisterService registerService(UserTransactions registerUser) {
    return new UserRegisterService(registerUser);
  }

  public UserLoginService loginService(UserTransactions loginUser) {
    return new UserLoginService(loginUser);
  }
}