package org.quarkus.routes.gym;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.errors.InvalidGymSearchException;
import org.quarkus.services.gym.GymSearchService;
import org.quarkus.validations.gym.GymSearchValidation;
import java.util.List;
import java.util.stream.Collectors;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de busca global de academias")
public class GymSearchRoute {
  @Inject
  GymSearchService database;

  @GET
  @Path("/search")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createGym(@QueryParam("query") String query, @QueryParam("page") @DefaultValue("0") int page) {
    return database.searchGyms(query, page)
      .onItem().transform(gyms -> {
        List<GymSearchValidation> response = gyms.stream()
          .map(gym -> new GymSearchValidation(gym.getName(), gym.getPhone()))
          .collect(Collectors.toList());

        return Response.status(OK).entity(response).build();
      })
      .onFailure(InvalidGymSearchException.class)
      .recoverWithItem(error -> Response.status(NOT_ACCEPTABLE)
        .entity(error.getMessage())
        .build()
      );
  }
}
