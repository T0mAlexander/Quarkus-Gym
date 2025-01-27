package org.quarkus.routes.user;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.errors.InvalidCredentialsException;
import org.quarkus.services.errors.NewSessionException;
import org.quarkus.services.user.TokenService;
import org.quarkus.services.user.UserLoginService;
import org.quarkus.validations.user.UserLoginValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;

@Path("/users")
@RegisterRestClient
@SuppressWarnings("unused")
public class UserLoginRoute {
  private static final Logger log = LoggerFactory.getLogger(UserLoginRoute.class);

  @Inject
  UserLoginService login;

  @Inject
  TokenService JWT;

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Tag(name = "Rota de login de usuários")
  public Uni<Response> loginUser(@Valid UserLoginValidation request, @CookieParam("token") String token) {
    return login.auth(request.email(), request.password(), token)
      .onItem().transformToUni(user ->
        JWT.generateToken(user).onItem()
          .transform(newToken -> {
            log.info("Usuário \"{}\" logou-se na aplicação!", user.getName());

            NewCookie tokenCookie = new NewCookie
              .Builder("token")
              .value(newToken)
              .path("/").maxAge((int) Duration.ofMinutes(15).toSeconds())
              .secure(true)
              .httpOnly(true)
              .build();

            return Response.status(OK)
              .cookie(tokenCookie)
              .entity("{\"token\": \"" + newToken + "\"}")
              .build();
            }
          )
      ).onFailure(NewSessionException.class)
      .recoverWithItem(
        error -> Response.status(FORBIDDEN)
          .entity(error.getMessage())
          .build()
      ).onFailure(InvalidCredentialsException.class)
      .recoverWithItem(
        error -> Response.status(UNAUTHORIZED)
          .entity(error.getMessage())
          .build()
      );
  }
}