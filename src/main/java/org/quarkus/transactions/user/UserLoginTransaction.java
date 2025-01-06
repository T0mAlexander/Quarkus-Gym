package org.quarkus.transactions.user;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.User;
import org.quarkus.repositories.UserRepository;

@ApplicationScoped
public class UserLoginTransaction implements PanacheRepository<User>, UserRepository {

  @Override
  public Uni<User> findByEmail(String email) {
    return find("email", email).firstResult();
  }

  @Override
  public Uni<User> create(User user) {
    return null;
  }
}