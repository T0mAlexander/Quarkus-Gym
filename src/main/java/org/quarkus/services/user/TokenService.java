package org.quarkus.services.user;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.User;
import java.time.Duration;

/**
 * Serviço de emissão de tokens no padrão JWT com prazo de validade em 7 dias.
 */
@ApplicationScoped
public class TokenService {

  /**
   * Gera um token JWT para o usuário fornecido.
   *
   * @param user O usuário para o qual o token será emitido.
   * @return O token JWT gerado.
   */
  public Uni<String> generateToken(User user) {
    return Uni.createFrom().item(() ->
      Jwt.upn(user.getEmail())
        .claim("id", user.getId())
        .expiresIn(Duration.ofDays(7))
        .sign()
    );
  }
}