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

  /**
   * Renova um token JWT existente.
   *
   * @param token O token JWT a ser renovado.
   * @return O novo token JWT.
   */
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

  /**
   * Valida um token JWT.
   *
   * @param token O token JWT a ser validado.
   * @return O ID do usuário se o token for válido, caso contrário, null.
   */
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

  /**
   * Revoga um token JWT.
   *
   * @return Uma Uni<Void> indicando a conclusão da revogação.
   */
  public Uni<Void> revokeToken() {
    return Uni.createFrom().voidItem();
  }

  /**
   * Verifica o cargo do usuário a partir do token JWT.
   *
   * @param token O token JWT a ser verificado.
   * @return O cargo do usuário.
   */
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