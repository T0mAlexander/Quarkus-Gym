package org.quarkus.services.user;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.User;

import java.time.Duration;
import java.util.UUID;

/**
 * Serviço de emissão de tokens no padrão JWT com expiração de 15 minutos.
 */

@ApplicationScoped
public class TokenService {

  @Inject
  JWTParser service;

  /**
   * Gera um token JWT para o usuário fornecido.
   *
   * @param user O usuário para o qual o token será emitido.
   * @return O token JWT gerado.
   */
  public Uni<String> generateToken(User user) {
    return Uni.createFrom().item(() ->
      Jwt.upn(user.getId().toString())
        .claim("id", user.getId().toString())
        .expiresIn(Duration.ofMinutes(15))
        .sign()
    );
  }

  public Uni<UUID> validateToken(String token) {
    return Uni.createFrom().item(() -> {
      try {
        var jwt = service.parse(token);
        String userId = jwt.getClaim("id");

        return UUID.fromString(userId);
      } catch (ParseException | IllegalArgumentException error) {
        return null;
      }
    });
  }
}