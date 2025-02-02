package org.quarkus.services.gym;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.Gym;
import org.quarkus.services.errors.InvalidGymSearchException;
import org.quarkus.transactions.GymTransactions;

import java.util.List;

/**
 * Serviço de busca de academias.
 * <p>
 * Esta classe define os métodos para buscar academias com base em uma consulta,
 * incluindo a validação da consulta e paginação.
 * </p>
 */

@ApplicationScoped
public class GymSearchService {
  private final GymTransactions service;

  @Inject
  public GymSearchService(GymTransactions service) {
    this.service = service;
  }

  /**
   * Busca academias com base em uma consulta.
   *
   * @param query Consulta de busca.
   * @param page Número da página para paginação.
   * @return A lista de academias encontradas.
   * @throws InvalidGymSearchException Se a consulta ou a página forem inválidas.
   */

  @WithTransaction
  public Uni<List<Gym>> searchGyms(String query, Integer page) {
    if (query == null || query.isEmpty() || page == null || page < 1) {
      return Uni.createFrom()
        .failure(new InvalidGymSearchException("Busca inválida por academias!"));
    }

    return service.searchGyms(query, page);
  }
}