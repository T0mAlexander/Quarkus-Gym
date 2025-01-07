package org.quarkus.routes.gym;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CONFLICT;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.errors.GymExistsException;
import org.quarkus.services.gym.GymCreationService;
import org.quarkus.validations.gym.GymCreationValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
public class GymCreationRoute {
  private static final Logger log = LoggerFactory.getLogger(GymCreationRoute.class);

  @Inject
  GymCreationService database;

  @POST
  @Path("/signup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createGym(@Valid GymCreationValidation request) {
    return database.create(
      request.getName(),
      request.getEmail(),
      request.getDescription(),
      request.getPhone(),
      request.getLatitude(),
      request.getLongitude()
      ).onItem()
      .transform(newGym -> {
        log.info("Academia \"{}\" registrou-se na aplicação!", newGym.getName());

        return Response.status(CREATED).entity(request).build();
      }
    ).onFailure(GymExistsException.class)
      .recoverWithItem(error -> Response.status(CONFLICT)
        .entity(error.getMessage())
        .build()
    );
  }
}