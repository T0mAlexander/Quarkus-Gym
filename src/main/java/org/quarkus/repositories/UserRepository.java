package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.models.User;

/**
 * Repositório para gerenciar operações de usuário.
 * <p>
 * Esta interface define métodos para operações de CRUD e consultas específicas
 * relacionadas a usuários.
 * </p>
 */

public interface UserRepository {

  /**
   * Encontra um usuário pelo seu email.
   *
   * @param email Email do usuário.
   * @return O usuário encontrado.
   */
  Uni<User> findByEmail(String email);

  /**
   * Cria um novo usuário.
   *
   * @param user Objeto de usuário a ser criado.
   * @return O usuário criado.
   */
  Uni<User> create(User user);
}