package org.quarkus.services.gym;

import io.quarkus.cache.CacheInvalidate;
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

/**
 * Serviço de criação de academias.
 * <p>
 * Esta classe define os métodos para criar novas academias,
 * incluindo a validação das coordenadas e a verificação de existência de academia.
 * </p>
 */

@SuppressWarnings("unused")
@ApplicationScoped
public class GymCreationService {
  private final GymTransactions service;

  @Inject
  public GymCreationService(GymTransactions service) {
    this.service = service;
  }

  /**
   * Cria uma nova academia.
   *
   * @param name Nome da academia.
   * @param email Email da academia.
   * @param description Descrição da academia.
   * @param phone Telefone da academia.
   * @param latitude Latitude da localização da academia.
   * @param longitude Longitude da localização da academia.
   * @return A academia criada.
   * @throws InvalidCoordsException Se as coordenadas forem inválidas.
   * @throws GymExistsException Se a academia já existir.
   */

  @WithTransaction
  @CacheInvalidate(cacheName = "gyms")
  public Uni<Gym> createGym(String name, String email, String description, String phone, Double latitude, Double longitude) {

    if (VincentyAlgorithm.validCoords(new Coordinates(latitude, longitude))) {
      return Uni.createFrom().failure(new InvalidCoordsException("Coordenadas inválidas!"));
    }

    return service.findByEmail(email).onItem().ifNotNull()
      .failWith(new GymExistsException("Esta academia já existe!"))
      .onItem().ifNull().switchTo(() -> {
          Gym newGym = new Gym();
          GeometryFactory postgis = new GeometryFactory();

          Point location = postgis.createPoint(
            new Coordinate(longitude, latitude)
          );

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