package org.quarkus.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.user.UserLoginService;
import org.quarkus.transactions.UserTransactions;
import org.quarkus.services.user.UserRegisterService;

/**
 * Fábrica para criar instâncias dos serviços de usuário.
 * <p>
 * Esta fábrica fornece métodos para criar serviços relacionados a usuários, incluindo
 * registro de usuários e login de usuários.
 * </p>
 */

@ApplicationScoped
@SuppressWarnings("unused")
public class UserFactory {

  /**
   * Cria uma instância de {@link UserRegisterService}.
   *
   * @param registerUser Transações de registro de usuário.
   * @return Uma nova instância de {@link UserRegisterService}.
   */
  public UserRegisterService registerService(UserTransactions registerUser) {
    return new UserRegisterService(registerUser);
  }

  /**
   * Cria uma instância de {@link UserLoginService}.
   *
   * @param loginUser Transações de login de usuário.
   * @return Uma nova instância de {@link UserLoginService}.
   */
  public UserLoginService loginService(UserTransactions loginUser) {
    return new UserLoginService(loginUser);
  }
}