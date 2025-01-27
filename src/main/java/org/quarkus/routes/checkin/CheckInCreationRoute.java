package org.quarkus.routes.checkin;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.checkin.CheckInCreationService;
import org.quarkus.services.user.TokenService;
import org.quarkus.validations.checkin.CheckInCreationValidation;

import java.util.UUID;

import static org.jboss.resteasy.reactive.RestResponse.Status.BAD_REQUEST;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.UNAUTHORIZED;

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de check-in na academia")
public class CheckInCreationRoute {
  @Inject
  CheckInCreationService service;

  @Inject
  TokenService jwt;

  @POST
  @Path("/{gymId}/check-in")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createCheckIn(@PathParam("gymId") UUID gymId, @Valid CheckInCreationValidation request, @Context HttpHeaders headers) {
    String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return Uni.createFrom().item(Response.status(UNAUTHORIZED).build());
    }

    String authToken = authHeader.substring("Bearer".length()).trim();

    return jwt.validateToken(authToken).onItem().transformToUni(userId -> {
      if (userId == null) {
        return Uni.createFrom().item(Response.status(UNAUTHORIZED).build());
      }

      return service.createCheckIn(
        userId, gymId,
        request.latitude(),
        request.longitude()
      ).onItem().transform(
        checkIn -> Response.status(OK)
          .entity("Check-in realizado na academia " + checkIn.getGym().getName() + "!")
          .build()
      ).onFailure().recoverWithItem(
        error -> Response.status(BAD_REQUEST)
          .entity(error.getMessage()).build()
      );
    });
  }
}