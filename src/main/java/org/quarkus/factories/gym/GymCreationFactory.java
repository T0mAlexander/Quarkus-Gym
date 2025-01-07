package org.quarkus.factories.gym;

import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.services.gym.GymCreationService;
import org.quarkus.transactions.gym.GymCreationTransaction;

@ApplicationScoped
@SuppressWarnings("unused")
public class GymCreationFactory {
  public GymCreationService service(GymCreationTransaction transaction) {
    return new GymCreationService(transaction);
  }
}
