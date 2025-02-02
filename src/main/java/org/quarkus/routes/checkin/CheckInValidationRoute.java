package org.quarkus.routes.checkin;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.checkin.CheckInValidationService;
import org.quarkus.services.user.TokenService;
import org.quarkus.utils.user.Role;

import java.util.UUID;

import static org.jboss.resteasy.reactive.RestResponse.Status.BAD_REQUEST;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.UNAUTHORIZED;

/**
 * Rota para validação de check-in em academias.
 * <p>
 * Esta classe define os endpoints para validar check-ins em academias,
 * incluindo a validação de token de administrador e a verificação de privilégios.
 * </p>
 */

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de validação de check-in na academia")
public class CheckInValidationRoute {
  @Inject
  CheckInValidationService service;

  @Inject
  TokenService jwt;

  /**
   * Valida um check-in em uma academia.
   *
   * @param checkInId Identificador do check-in.
   * @param header Cabeçalhos HTTP.
   * @param cookie Cookie de autenticação.
   * @return A resposta da validação do check-in.
   */

  @PATCH
  @Path("/validate/{checkInId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> validateCheckIn(@PathParam("checkInId") UUID checkInId, @Context HttpHeaders header, @CookieParam("token") Cookie cookie) {
    String authHeader = header.getHeaderString(HttpHeaders.AUTHORIZATION);
    String authToken;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      if (cookie != null) {
        authToken = cookie.getValue();
      } else {
        return Uni.createFrom()
          .item(Response.status(UNAUTHORIZED)
            .entity("Check-in não confirmado. Nenhum administrador logado!")
            .build()
          );
      }
    } else {
      authToken = authHeader.substring("Bearer".length()).trim();
    }

    return jwt.validateToken(authToken).onItem()
      .transformToUni(adminId -> {
          if (adminId == null) {
            return Uni.createFrom().item(Response.status(BAD_REQUEST).entity("O token é inválido ou expirou!").build());
          }

          return jwt.checkRole(authToken).onItem().transformToUni(role -> {
            if (role != Role.ADMIN) {
              return Uni.createFrom().item(Response.status(UNAUTHORIZED).entity("Este usuário não possui privilégios administrativos para validar check-ins!").build());
            }

            return service.validateCheckIn(checkInId, authToken)
              .onItem().transform(
                checkIn -> Response.status(OK).entity("O check-in foi validado com sucesso!").build()
              ).onFailure().recoverWithItem(
                error -> Response.status(UNAUTHORIZED)
                  .entity(error.getMessage()).build()
              );
          }
        );
      }
    );
  }
}