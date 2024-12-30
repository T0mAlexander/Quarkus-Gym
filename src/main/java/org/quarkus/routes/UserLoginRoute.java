package org.quarkus.routes;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.models.User;
import org.quarkus.services.TokenService;
import org.quarkus.services.UserLoginService;
import org.quarkus.services.errors.InvalidCredentialsException;
import org.quarkus.validations.UserLoginValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users")
@RegisterRestClient
public class UserLoginRoute {
  private static final Logger log = LoggerFactory.getLogger(UserLoginRoute.class);

  @Inject
  UserLoginService login;

  @Inject
  TokenService jwt;

  /**
   * Endpoint para login de usuários.
   *
   * @param request Dados de login do usuário.
   * @return Resposta HTTP com o token JWT ou mensagem de erro.
   */
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(@Valid UserLoginValidation request) {
    try {
      User user = login.auth(request.getEmail(), request.getPassword());
      String token = jwt.generateToken(user);

      log.info("Usuário \"{}\" logou-se na aplicação!", user.getName());

      return Response.ok().entity(token).build();
    } catch (InvalidCredentialsException error) {
      return Response.status(Response.Status.UNAUTHORIZED).entity(
        error.getMessage()
      ).build();
    }
  }
}
