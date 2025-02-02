package org.quarkus.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.gym.GymCreationService;
import org.quarkus.services.gym.GymNearbyService;
import org.quarkus.services.gym.GymSearchService;
import org.quarkus.transactions.GymTransactions;

/**
 * Fábrica para criar instâncias dos serviços de academia.
 * <p>
 * Esta fábrica fornece métodos para criar serviços relacionados a academias, incluindo
 * criação de academias, busca de academias e academias próximas.
 * </p>
 */

@ApplicationScoped
@SuppressWarnings("unused")
public class GymFactory {

  /**
   * Cria uma instância de {@link GymCreationService}.
   *
   * @param createGym Transações de criação de academia.
   * @return Uma nova instância de {@link GymCreationService}.
   */
  public GymCreationService createService(GymTransactions createGym) {
    return new GymCreationService(createGym);
  }

  /**
   * Cria uma instância de {@link GymSearchService}.
   *
   * @param searchGym Transações de busca de academia.
   * @return Uma nova instância de {@link GymSearchService}.
   */
  public GymSearchService searchService(GymTransactions searchGym) {
    return new GymSearchService(searchGym);
  }

  /**
   * Cria uma instância de {@link GymNearbyService}.
   *
   * @param nearbyGym Transações de academias próximas.
   * @return Uma nova instância de {@link GymNearbyService}.
   */
  public GymNearbyService nearbyService(GymTransactions nearbyGym) {
    return new GymNearbyService(nearbyGym);
  }
}