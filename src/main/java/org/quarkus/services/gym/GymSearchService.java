package org.quarkus.services.gym;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import org.quarkus.models.Gym;
import org.quarkus.transactions.GymTransactions;

import java.util.List;

@ApplicationScoped
public class GymSearchService {
  private final GymTransactions service;

  @Inject
  public GymSearchService(GymTransactions service) {
    this.service = service;
  }

  @WithTransaction
  public Uni<List<Gym>> searchGyms(String query, int page) {
    return Uni.createFrom().item(() -> {
      return service.searchGyms(query, page);
    })
  }
}
