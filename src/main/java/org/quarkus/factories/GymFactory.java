package org.quarkus.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.gym.GymCreationService;
import org.quarkus.services.gym.GymSearchService;
import org.quarkus.transactions.GymTransactions;

@ApplicationScoped
@SuppressWarnings("unused")
public class GymFactory {
  public GymCreationService createService(GymTransactions createGym) {
    return new GymCreationService(createGym);
  }

  public GymSearchService searchService(GymTransactions searchGym) {
    return new GymSearchService(searchGym);
  }
}
