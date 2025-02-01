package org.quarkus.routes.user;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.quarkus.services.user.TokenService;

import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@Path("/users")
@RegisterRestClient
@SuppressWarnings("unused")
@Tag(name = "Rota de logout")
public class UserLogoutRoute {
  @Inject
  TokenService jwt;

  @POST
  @Path("/logout")
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<Response> logout(@Context HttpHeaders header, @CookieParam("token") Cookie cookie) {
    String authHeader = header.getHeaderString(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      if (cookie == null) {
        return Uni.createFrom()
          .item(
            Response.status(OK).entity("Nenhum usuário está logado!").build()
          );
      }
    }

    return jwt.revokeToken().onItem().transform(
      ignored -> Response.status(OK)
        .entity("Logout realizado com sucesso!")
        .cookie(new NewCookie.Builder("token")
          .path("/")
          .maxAge(0)
          .build()
        ).build()
    );
  }
}