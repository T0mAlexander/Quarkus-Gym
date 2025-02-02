package org.quarkus.services.checkin;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.transactions.CheckInTransactions;
import org.quarkus.validations.checkin.CheckInHistoryValidation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço de histórico de check-ins.
 * <p>
 * Esta classe define os métodos para recuperar o histórico de check-ins de usuários,
 * permitindo a visualização dos check-ins realizados em academias.
 * </p>
 */

@ApplicationScoped
@SuppressWarnings("unused")
public class CheckInHistoryService {
  private final CheckInTransactions service;

  @Inject
  public CheckInHistoryService(CheckInTransactions service) {
    this.service = service;
  }

  /**
   * Recupera o histórico de check-ins de um usuário.
   *
   * @param userId ID do usuário.
   * @param page Número da página para paginação.
   * @return A lista de validações de histórico de check-ins.
   */

  @WithTransaction
  public Uni<List<CheckInHistoryValidation>> checkInHistory(UUID userId, int page) {
    return service.userHistory(userId, page)
      .onItem().ifNull().failWith(
        new RuntimeException("Não há registros de check-in!")
      ).onItem()
      .transform(checkIns -> checkIns.stream()
        .map(checkIn -> new CheckInHistoryValidation(
          checkIn.getGym().getName(),
          checkIn.getGym().getLocation().getX(),
          checkIn.getGym().getLocation().getY(),
          checkIn.getCreationDate(),
          checkIn.getValidationDate()
        ))
        .collect(Collectors.toList())
      );
  }
}