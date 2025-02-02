package org.quarkus.transactions;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.User;
import org.quarkus.repositories.UserRepository;

/**
 * Transações de usuários.
 * <p>
 * Esta classe define os métodos para gerenciar as transações de usuários,
 * incluindo a busca por email e a criação de novos usuários.
 * </p>
 */

@ApplicationScoped
public class UserTransactions implements PanacheRepository<User>, UserRepository {

  @Override
  public Uni<User> findByEmail(String email) {
    return find("email", email).firstResult();
  }

  @Override
  public Uni<User> create(User user) {
    return getSession().chain(session -> session.merge(user));
  }
}