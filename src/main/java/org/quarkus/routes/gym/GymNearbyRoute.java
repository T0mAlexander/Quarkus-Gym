package org.quarkus.routes.gym;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.algorithms.Coordinates;
import org.quarkus.algorithms.VincentyAlgorithm;
import org.quarkus.services.errors.InvalidCoordsException;
import org.quarkus.services.gym.GymNearbyService;
import org.quarkus.validations.gym.GymNearbyValidation;
import java.util.List;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.NOT_ACCEPTABLE;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
public class GymNearbyRoute {
  @Inject
  GymNearbyService service;

  @GET
  @Path("/nearby")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> nearbyGyms(@QueryParam("latitude") Double latitude, @QueryParam("longitude") Double longitude) {
    return service.nearbyGyms(latitude, longitude)
      .onItem().transform(gyms -> {
        List<GymNearbyValidation> response = gyms.stream()
          .map(gym -> {
            double distanceFromUser = VincentyAlgorithm.distance(
              new Coordinates(latitude, longitude),
              new Coordinates(
                gym.getLocation().getY(),
                gym.getLocation().getX()
              )
            );

            String formattedDistance = VincentyAlgorithm.formatDistance(distanceFromUser);

            return new GymNearbyValidation(gym.getName(), gym.getPhone(), formattedDistance);
          }).toList();
        return Response.status(OK).entity(response).build();
      }).onFailure(InvalidCoordsException.class)
      .recoverWithItem(error -> Response.status(NOT_ACCEPTABLE)
        .entity(error.getMessage()).build());
  }
}