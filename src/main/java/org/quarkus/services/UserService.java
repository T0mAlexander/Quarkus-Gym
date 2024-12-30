package org.quarkus.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.database.UserLoginTransaction;
import org.quarkus.database.UsersRegisterTransaction;
import org.quarkus.models.User;
import org.quarkus.services.errors.UserExistsException;
import org.quarkus.validations.UserRegisterValidation;

@ApplicationScoped
public class UserService {
  private final UsersRegisterTransaction database;

  public UserService(UsersRegisterTransaction database) {
    this.database = database;
  }

  public User create(UserRegisterValidation request) throws UserExistsException{
    String passwordHash = BCrypt.withDefaults().hashToString(6, request.getPassword().toCharArray());

    User existingUser = database.findByEmail(request.getEmail());

    if (existingUser != null) {
      throw new UserExistsException("Este usuário já está cadastrado!");
    }

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordHash);

    return database.create(user);
  }
}