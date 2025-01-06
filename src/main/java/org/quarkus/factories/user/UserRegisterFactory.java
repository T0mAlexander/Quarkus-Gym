package org.quarkus.factories.user;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.transactions.user.UserRegisterTransaction;
import org.quarkus.services.user.UserRegisterService;

@ApplicationScoped
@SuppressWarnings("unused")
public class UserRegisterFactory {
  public UserRegisterService service(UserRegisterTransaction transaction) {
    return new UserRegisterService(transaction);
  }
}