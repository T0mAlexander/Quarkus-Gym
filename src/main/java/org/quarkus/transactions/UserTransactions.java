package org.quarkus.transactions;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.objects.User;
import org.quarkus.repositories.UserRepository;

@ApplicationScoped
public class UserTransactions implements PanacheRepository<User>, UserRepository {
  @Override
  public Uni<User> findByEmail(String email) {
    return find("email", email).firstResult();
  }

  @Override
  public Uni<User> create(User user) {
    return persist(user);
  }
}