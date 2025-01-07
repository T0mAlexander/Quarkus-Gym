package org.quarkus.services.gym;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.Gym;
import org.quarkus.services.errors.GymExistsException;
import org.quarkus.transactions.gym.GymCreationTransaction;

@SuppressWarnings("unused")
@ApplicationScoped
public class GymCreationService {
  private final GymCreationTransaction database;

  @Inject
  public GymCreationService(GymCreationTransaction database) {
    this.database = database;
  }

  @WithTransaction
  public Uni<Gym> create(String name, String email, String description, String phone, Double latitude, Double longitude) {


    return database.findByEmail(email).onItem().ifNotNull()
      .failWith(new GymExistsException("Esta academia já existe!"))
      .onItem().ifNull().switchTo(() -> {
        Gym newGym = new Gym();

        newGym.setName(name);
        newGym.setEmail(email);
        newGym.setDescription(description);
        newGym.setPhone(phone);
        newGym.setLatitude(latitude);
        newGym.setLongitude(longitude);

        return database.create(newGym);
      }
    );
  }
}
