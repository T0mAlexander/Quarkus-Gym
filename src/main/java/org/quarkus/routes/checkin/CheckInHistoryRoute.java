package org.quarkus.routes.checkin;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.checkin.CheckInHistoryService;
import org.quarkus.services.user.TokenService;

/**
 * Rota para histórico de check-ins.
 * <p>
 * Esta classe define os endpoints para obter o histórico de check-ins de um usuário,
 * incluindo a validação de token de usuário.
 * </p>
 */

@Path("/checkins")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de histórico de check-ins")
public class CheckInHistoryRoute {
  @Inject
  CheckInHistoryService service;

  @Inject
  TokenService jwt;

  /**
   * Obtém o histórico de check-ins de um usuário.
   *
   * @param headers Cabeçalhos HTTP.
   * @param tokenCookie Cookie de autenticação.
   * @param page Número da página para paginação.
   * @return A resposta com o histórico de check-ins.
   */

  @GET
  @Path("/history")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> userCheckInHistory(@Context HttpHeaders headers, @CookieParam("token") Cookie tokenCookie, @QueryParam("page") @DefaultValue("1") int page) {
    String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
    String authToken;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      if (tokenCookie != null) {
        authToken = tokenCookie.getValue();
      } else {
        return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não está logado!").build());
      }
    } else {
      authToken = authHeader.substring("Bearer".length()).trim();
    }

    return jwt.validateToken(authToken).onItem().transformToUni(userId -> {
      if (userId == null) {
        return Uni.createFrom().item(Response.status(Response.Status.FORBIDDEN).entity("Token inválido ou expirado!").build());
      }

      return service.checkInHistory(userId, page)
        .onItem().transform(checkIns -> Response.ok(checkIns).build())
        .onFailure().recoverWithItem(error -> Response.status(Response.Status.BAD_REQUEST).entity(error.getMessage()).build());
    });
  }
}