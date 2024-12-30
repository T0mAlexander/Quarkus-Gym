package org.quarkus.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.quarkus.models.User;

@ApplicationScoped
public class UserLoginTransaction implements PanacheRepository<User> {

  @Transactional
  public User findUser(String email, String password) {
    User user = find("email", email).firstResult();

    if (user != null && BCrypt.verifyer()
      .verify(
        password.toCharArray(),
        user.getPassword()
      ).verified) {
      return user;
    } else {
      return null;
    }
  }
}