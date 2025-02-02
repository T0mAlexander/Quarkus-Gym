package org.quarkus.services.gym;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.algorithms.Coordinates;
import org.quarkus.algorithms.VincentyAlgorithm;
import org.quarkus.models.Gym;
import org.quarkus.services.errors.InvalidCoordsException;
import org.quarkus.transactions.GymTransactions;

import java.util.List;

/**
 * Serviço de academias próximas.
 * <p>
 * Esta classe define os métodos para encontrar academias próximas à localização do usuário,
 * incluindo a validação das coordenadas fornecidas.
 * </p>
 */

@ApplicationScoped
public class GymNearbyService {
  private final GymTransactions service;

  @Inject
  public GymNearbyService(GymTransactions service) {
    this.service = service;
  }

  /**
   * Encontra academias próximas à localização do usuário.
   *
   * @param userLatitude Latitude da localização do usuário.
   * @param userLongitude Longitude da localização do usuário.
   * @return A lista de academias próximas.
   * @throws InvalidCoordsException Se as coordenadas forem inválidas.
   */

  @WithTransaction
  public Uni<List<Gym>> nearbyGyms(Double userLatitude, Double userLongitude) {
    if (VincentyAlgorithm.validCoords(new Coordinates(userLatitude, userLongitude))) {
      return Uni.createFrom().failure(
        new InvalidCoordsException("Coordenadas inválidas!")
      );
    }

    return service.findCloseGyms(userLatitude, userLongitude);
  }
}