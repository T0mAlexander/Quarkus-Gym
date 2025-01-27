package org.quarkus.services.gym;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.Gym;
import org.quarkus.services.errors.InvalidCoordsException;
import org.quarkus.transactions.GymTransactions;

import java.util.List;

@ApplicationScoped
public class GymNearbyService {
  private final GymTransactions service;

  @Inject
  public GymNearbyService(GymTransactions service) {
    this.service = service;
  }

  @WithTransaction
  public Uni<List<Gym>> nearbyGyms(Double userLatitude, Double userLongitude) {
    if (userLatitude < -90 || userLatitude > 90 || userLongitude < -180 || userLongitude > 180) {
      return Uni.createFrom().failure(
        new InvalidCoordsException("Coordenadas inválidas!")
      );
    }

    return service.findCloseGyms(userLatitude, userLongitude);
  }
}