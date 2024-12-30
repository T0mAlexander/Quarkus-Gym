package org.quarkus.database;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.quarkus.models.User;

@ApplicationScoped
public class UsersRegisterTransaction implements PanacheRepository<User> {
  public User findById(Long id) {
    return find("id", id).firstResult();
  }

  public User findByEmail(String email) {
    return find("email", email).firstResult();
  }

  @Transactional
  public User create(User user) {
    persist(user);

    return user;
  }
}