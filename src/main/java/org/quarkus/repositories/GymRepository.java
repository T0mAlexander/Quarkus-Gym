package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.objects.Gym;
import java.util.List;

public interface GymRepository {
  Uni<Gym> findByEmail(String email);
  Uni<List<Gym>> searchGyms(String query, int page);
  Uni<List<Gym>> findCloseGyms(Double latitude, Double longitude);
  Uni<Gym> create(Gym gym);
}
