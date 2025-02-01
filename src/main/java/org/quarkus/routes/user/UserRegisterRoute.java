package org.quarkus.routes.user;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.errors.UserExistsException;
import org.quarkus.services.user.UserRegisterService;
import org.quarkus.validations.user.UserRegisterValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CONFLICT;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED;

@Path("/users")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de registro de novos usuários")
public class UserRegisterRoute {
  private static final Logger log = LoggerFactory.getLogger(UserRegisterRoute.class);

  @Inject
  UserRegisterService database;

  @POST
  @Path("/signup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createUser(@Valid UserRegisterValidation request) {
    return database.create(
        request.name(),
        request.email(),
        request.password(),
        request.role()
      ).onItem()
      .transform(newUser -> {
        log.info("Usuário \"{}\" registrou-se na aplicação!", newUser.getName());

        UserRegisterValidation response = new UserRegisterValidation(
          newUser.getName(),
          newUser.getEmail(),
          newUser.getPassword(),
          newUser.getRole()
        );

        return Response.status(CREATED).entity(response).build();
      }).onFailure(UserExistsException.class)
      .recoverWithItem(
        error -> Response.status(CONFLICT)
          .entity(
            error.getMessage()
          ).build());
  }
}