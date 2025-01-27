package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.models.Gym;
import java.util.List;
import java.util.UUID;

public interface GymRepository {
  Uni<Gym> findByEmail(String email);
  Uni<List<Gym>> searchGyms(String query, int page);
  Uni<List<Gym>> findCloseGyms(Double latitude, Double longitude);
  Uni<Gym> findById(UUID id);
  Uni<Gym> create(Gym gym);
}
