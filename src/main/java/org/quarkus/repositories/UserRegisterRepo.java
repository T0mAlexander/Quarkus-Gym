package org.quarkus.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.User;

@ApplicationScoped
public interface UserRegisterRepo {
  User findById(Long id);
  User findByEmail(String email);
  User create(User user);
}
