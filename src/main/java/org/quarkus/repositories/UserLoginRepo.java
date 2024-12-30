package org.quarkus.repositories;

import org.quarkus.models.User;

public interface UserLoginRepo {
  User findUser(String email, String password);
  User findByEmail(String email);
}
