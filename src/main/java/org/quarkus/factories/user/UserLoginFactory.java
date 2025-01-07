package org.quarkus.factories.user;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.user.UserLoginService;
import org.quarkus.transactions.UserTransactions;

@ApplicationScoped
@SuppressWarnings("unused")
public class UserLoginFactory {
  public UserLoginService service(UserTransactions transaction) {
    return new UserLoginService(transaction);
  }
}