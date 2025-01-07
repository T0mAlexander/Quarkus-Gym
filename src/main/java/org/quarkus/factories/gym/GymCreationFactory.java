package org.quarkus.factories.gym;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.gym.GymCreationService;
import org.quarkus.transactions.GymTransactions;

@ApplicationScoped
@SuppressWarnings("unused")
public class GymCreationFactory {
  public GymCreationService service(GymTransactions transaction) {
    return new GymCreationService(transaction);
  }
}
