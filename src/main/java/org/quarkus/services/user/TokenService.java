package org.quarkus.services.user;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.User;
import org.quarkus.utils.user.Role;

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
   * @return Retorna o token de autenticação nos cookies.
   */
  public Uni<String> generateToken(User user) {
    return Uni.createFrom().item(() ->
      Jwt.upn(user.getId().toString())
        .claim("id", user.getId().toString())
        .claim("role", user.getRole().toString())
        .expiresIn(Duration.ofMinutes(15))
        .sign()
    );
  }

  public Uni<String> renewToken(String token) {
    return validateToken(token).onItem().transformToUni(userId -> {
      if (userId == null) {
        return Uni.createFrom().failure(
          new IllegalArgumentException("Token inválido ou expirado!")
        );
      }

      return checkRole(token).onItem().transformToUni(role -> {
        User user = new User();
        user.setId(userId);
        user.setRole(role);

        return generateToken(user);
      });
    });
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

  public Uni<Void> revokeToken() {
    return Uni.createFrom().voidItem();
  }

  public Uni<Role> checkRole(String token) {
    return Uni.createFrom().item(() -> {
      try {
        var jwt = service.parse(token);
        String role = jwt.getClaim("role");

        if (role == null) {
          throw new IllegalArgumentException("O cargo está vazio!");
        }

        return Role.valueOf(role);
      } catch (ParseException | IllegalArgumentException error) {
        return null;
      }
    });
  }
}