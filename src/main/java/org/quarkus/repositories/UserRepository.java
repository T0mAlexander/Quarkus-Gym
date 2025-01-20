package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.objects.User;

public interface UserRepository {
  Uni<User> findByEmail(String email);
  Uni<User> create(User user);
}
