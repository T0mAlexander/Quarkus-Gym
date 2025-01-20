package org.quarkus.transactions;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.objects.Gym;
import org.quarkus.repositories.GymRepository;
import java.util.List;

@ApplicationScoped
public class GymTransactions implements PanacheRepository<Gym>, GymRepository {
  @Override
  public Uni<Gym> findByEmail(String email) {
    return find("email", email).firstResult();
  }

  @Override
  public Uni<List<Gym>> searchGyms(String query, int page) {
    if (page < 1) {
      return Uni.createFrom().failure(new IllegalArgumentException("Número de página inválido!"));
    }

    return find("name LIKE ?1", "%" + query + "%")
      .page(Page.of(page - 1, 20)).list();
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
