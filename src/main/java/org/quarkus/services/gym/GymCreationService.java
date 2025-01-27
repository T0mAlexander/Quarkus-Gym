package org.quarkus.services.gym;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.quarkus.algorithms.Coordinates;
import org.quarkus.algorithms.VincentyAlgorithm;
import org.quarkus.models.Gym;
import org.quarkus.services.errors.GymExistsException;
import org.quarkus.services.errors.InvalidCoordsException;
import org.quarkus.transactions.GymTransactions;

@SuppressWarnings("unused")
@ApplicationScoped
public class GymCreationService {
  private final GymTransactions service;

  @Inject
  public GymCreationService(GymTransactions service) {
    this.service = service;
  }

  @WithTransaction
  public Uni<Gym> createGym(String name, String email, String description, String phone, Double latitude, Double longitude) {

    if (VincentyAlgorithm.validCoords(new Coordinates(latitude, longitude))) {
      return Uni.createFrom().failure(new InvalidCoordsException("Coordenadas inválidas!"));
    }

    return service.findByEmail(email).onItem().ifNotNull()
      .failWith(new GymExistsException("Esta academia já existe!"))
      .onItem().ifNull().switchTo(() -> {
        Gym newGym = new Gym();
        GeometryFactory postgis = new GeometryFactory();

        Point location = postgis.createPoint(new Coordinate(longitude, latitude));

        newGym.setName(name);
        newGym.setEmail(email);
        newGym.setDescription(description);
        newGym.setPhone(phone);
        newGym.setLocation(location);

        return service.create(newGym);
      }
    );
  }
}
