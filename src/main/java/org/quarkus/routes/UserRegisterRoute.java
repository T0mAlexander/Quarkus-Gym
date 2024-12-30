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
import org.quarkus.services.UserService;
import org.quarkus.services.errors.UserExistsException;
import org.quarkus.validations.UserRegisterValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Endpoint de registro de novos usuários. <br>
 * Caso o usuário já exista, será retornado um código <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/409">{@code HTTP 409}</a>.
 */

@Path("/users")
@RegisterRestClient
public class UserRegisterRoute {
  private static final Logger log = LoggerFactory.getLogger(UserRegisterRoute.class);

  @Inject
  UserService database;

  @POST
  @Path("/signup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(@Valid UserRegisterValidation user) {
    try {
      database.create(user);

      log.info("Usuário \"{}\" registrou-se na aplicação!", user.getName());

      return Response.status(Response.Status.CREATED).entity(user).build();
    } catch (UserExistsException error) {
      return Response.status(Response.Status.CONFLICT).entity(
        error.getMessage()
      ).build();
    }
  }
}
