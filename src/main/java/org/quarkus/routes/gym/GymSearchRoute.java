package org.quarkus.routes.gym;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.BAD_REQUEST;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.errors.InvalidGymSearch;
import org.quarkus.services.gym.GymSearchService;
import org.quarkus.validations.gym.GymSearchValidation;

import java.util.List;
import java.util.stream.Collectors;

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
public class GymSearchRoute {
  @Inject
  GymSearchService database;

  @GET
  @Path("/search")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createGym(@QueryParam("query") String query, @DefaultValue("0") int page) {

    return database.searchGyms(query, page)
      .onItem()
      .transform(gyms -> {
        List<GymSearchValidation> response = gyms.stream()
          .map(gym -> {
            GymSearchValidation gymProps = new GymSearchValidation();

            gymProps.setName(gym.getName());
            gymProps.setPhone(gym.getPhone());

            return gymProps;
          }).collect(Collectors.toList());

        return Response.status(OK).entity(response).build();
      }).onFailure(InvalidGymSearch.class)
      .recoverWithItem(error -> Response.status(BAD_REQUEST)
        .entity(error.getMessage())
        .build()
      );
  }
}
