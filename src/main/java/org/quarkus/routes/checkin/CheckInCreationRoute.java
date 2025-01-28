package org.quarkus.routes.checkin;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.checkin.CheckInCreationService;
import org.quarkus.services.errors.MaxDistanceException;
import org.quarkus.services.user.TokenService;
import org.quarkus.validations.checkin.CheckInCreationValidation;

import java.util.UUID;

import static org.jboss.resteasy.reactive.RestResponse.Status.FORBIDDEN;
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
  public Uni<Response> createCheckIn(@PathParam("gymId") UUID gymId, @Valid CheckInCreationValidation request, @Context HttpHeaders headers, @CookieParam("token") Cookie cookie) {
    String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
    String authToken;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      if (cookie != null) {
        authToken = cookie.getValue();
      } else {
        return Uni.createFrom().item(Response.status(UNAUTHORIZED).entity("Check-in não realizado. O usuário não está logado!").build());
      }
    } else {
      authToken = authHeader.substring("Bearer".length()).trim();
    }

    return jwt.validateToken(authToken).onItem().transformToUni(userId -> {
      if (userId == null) {
        return Uni.createFrom().item(Response.status(FORBIDDEN).entity("Token inválido ou expirado!").build());
      }

      return service.createCheckIn(
        userId, gymId,
        request.latitude(),
        request.longitude()
      ).onItem().transform(
        checkIn -> Response.status(OK).entity("Check-in realizado na academia " + checkIn.getGym().getName() + "!").build()).onFailure(MaxDistanceException.class).recoverWithItem(error -> Response.status(UNAUTHORIZED).entity(error.getMessage()).build()
      ).onFailure().recoverWithItem(
        error -> Response.status(UNAUTHORIZED)
          .entity(error.getMessage()).build()
      );
    });
  }
}