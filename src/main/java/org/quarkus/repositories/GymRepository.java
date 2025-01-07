package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.models.Gym;
import java.util.List;

public interface GymRepository {
  Uni<Gym> findByEmail(String email);
  Uni<Gym> searchGyms(String query, int page);
  Uni<List<Gym>> findCloseGyms(Double latitude, Double longitude);
  Uni<Gym> create(Gym gym);
}
