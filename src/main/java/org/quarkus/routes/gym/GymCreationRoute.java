package org.quarkus.routes.gym;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.errors.GymExistsException;
import org.quarkus.services.errors.InvalidCoordsException;
import org.quarkus.services.gym.GymCreationService;
import org.quarkus.services.user.TokenService;
import org.quarkus.utils.user.Role;
import org.quarkus.validations.gym.GymCreationValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;

/**
 * Rota para registro de academias.
 * <p>
 * Esta classe define os endpoints para registrar novas academias,
 * incluindo a validação de token de administrador e a verificação de privilégios.
 * </p>
 */

@Path("/gyms")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de registro de academia")
public class GymCreationRoute {
  private static final Logger log = LoggerFactory.getLogger(GymCreationRoute.class);

  @Inject
  GymCreationService database;

  @Inject
  TokenService jwt;

  /**
   * Registra uma nova academia.
   *
   * @param request Dados de validação do registro da academia.
   * @param header Cabeçalhos HTTP.
   * @param cookie Cookie de autenticação.
   * @return A resposta do registro da academia.
   */

  @POST
  @Path("/signup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createGym(@Valid GymCreationValidation request, @Context HttpHeaders header, @CookieParam("token") Cookie cookie) {

    String authHeader = header.getHeaderString(HttpHeaders.AUTHORIZATION);
    String authToken;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      if (cookie != null) {
        authToken = cookie.getValue();
      } else {
        return Uni.createFrom()
          .item(Response.status(UNAUTHORIZED)
            .entity("Registro de academia não realizado. Nenhum usuário logado!")
            .build()
          );
      }
    } else {
      authToken = authHeader.substring("Bearer".length()).trim();
    }

    return jwt.validateToken(authToken).onItem()
      .transformToUni(userId -> {
          if (userId == null) {
            return Uni.createFrom()
              .item(Response.status(FORBIDDEN).entity("Token inválido ou expirado").build()
              );
          }

          return jwt.checkRole(authToken).onItem().transformToUni(role -> {
            if (role != Role.ADMIN) {
              return Uni.createFrom().item(Response.status(FORBIDDEN)
                .entity("Apenas administradores podem cadastrar academias!")
                .build()
              );
            }

            return database.createGym(
                request.name(),
                request.email(),
                request.description(),
                request.phone(),
                request.latitude(),
                request.longitude()
              ).onItem()
              .transform(newGym -> {
                  log.info("Academia \"{}\" registrou-se na aplicação!", newGym.getName());

                  return Response.status(CREATED).entity(request).build();
                }
              ).onFailure(GymExistsException.class)
              .recoverWithItem(error -> Response.status(CONFLICT)
                .entity(error.getMessage()).build()
              ).onFailure(InvalidCoordsException.class)
              .recoverWithItem(error -> Response.status(BAD_REQUEST)
                .entity(error.getMessage()).build()
              );
          });
        }
      );
  }
}