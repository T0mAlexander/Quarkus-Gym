package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.models.Gym;
import java.util.List;
import java.util.UUID;

/**
 * Repositório para gerenciar operações de academia.
 * <p>
 * Esta interface define métodos para operações de CRUD e consultas específicas
 * relacionadas a academias.
 * </p>
 */

public interface GymRepository {

  /**
   * Encontra uma academia pelo seu email.
   *
   * @param email Email da academia.
   * @return A academia encontrada.
   */
  Uni<Gym> findByEmail(String email);

  /**
   * Pesquisa academias com base em uma consulta.
   *
   * @param query Consulta de pesquisa.
   * @param page Número da página para paginação.
   * @return Uma lista de academias que correspondem à consulta.
   */
  Uni<List<Gym>> searchGyms(String query, int page);

  /**
   * Encontra academias próximas a uma localização específica.
   *
   * @param latitude Latitude da localização.
   * @param longitude Longitude da localização.
   * @return Uma lista de academias próximas.
   */
  Uni<List<Gym>> findCloseGyms(Double latitude, Double longitude);

  /**
   * Encontra uma academia pelo seu identificador.
   *
   * @param id Identificador da academia.
   * @return A academia encontrada.
   */
  Uni<Gym> findById(UUID id);

  /**
   * Cria uma nova academia.
   *
   * @param gym Objeto de academia a ser criado.
   * @return A academia criada.
   */
  Uni<Gym> create(Gym gym);
}