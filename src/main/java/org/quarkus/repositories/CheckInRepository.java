package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.models.CheckIn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositório para gerenciar operações de check-in.
 * <p>
 * Esta interface define métodos para operações de CRUD e consultas específicas
 * relacionadas a check-ins de usuários.
 * </p>
 */

public interface CheckInRepository {

  /**
   * Obtém o histórico de check-ins de um usuário.
   *
   * @param id Identificador do usuário.
   * @param page Número da página para paginação.
   * @return Uma lista de check-ins do usuário.
   */
  Uni<List<CheckIn>> userHistory(UUID id, int page);

  /**
   * Encontra o check-in anterior de um usuário em uma data específica.
   *
   * @param userId Identificador do usuário.
   * @param date Data para verificar o check-in anterior.
   * @return O check-in anterior do usuário.
   */
  Uni<CheckIn> findPreviousCheckIn(UUID userId, LocalDateTime date);

  /**
   * Cria um novo check-in.
   *
   * @param checkIn Objeto de check-in a ser criado.
   * @return O check-in criado.
   */
  Uni<CheckIn> create(CheckIn checkIn);

  /**
   * Encontra um check-in pelo seu identificador.
   *
   * @param checkInId Identificador do check-in.
   * @return O check-in encontrado.
   */
  Uni<CheckIn> findById(UUID checkInId);
}