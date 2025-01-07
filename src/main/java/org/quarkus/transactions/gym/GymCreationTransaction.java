package org.quarkus.transactions.gym;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.Gym;
import org.quarkus.repositories.GymRepository;
import java.util.List;

@ApplicationScoped
public class GymCreationTransaction implements PanacheRepository<Gym>, GymRepository {
  @Override
  public Uni<Gym> findByEmail(String email) {
    return find("email", email).firstResult();
  }

  @Override
  public Uni<Gym> searchGyms(String query, int page) {
    return null;
  }

  @Override
  public Uni<List<Gym>> findCloseGyms(Double latitude, Double longitude) {
    return null;
  }

  @Override
  public Uni<Gym> create(Gym gym) {
    return persist(gym);
  }
}
