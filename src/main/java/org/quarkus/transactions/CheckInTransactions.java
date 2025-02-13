package org.quarkus.transactions;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.CheckIn;
import org.quarkus.repositories.CheckInRepository;
import org.quarkus.utils.checkin.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Transações de Check-In.
 * <p>
 * Esta classe define os métodos para gerenciar as transações de check-in,
 * incluindo o histórico de check-ins do usuário, busca de check-ins anteriores
 * e criação de novos check-ins.
 * </p>
 */

@ApplicationScoped
public class CheckInTransactions implements PanacheRepository<CheckIn>, CheckInRepository {

  @Override
  public Uni<List<CheckIn>> userHistory(UUID id, int page) {
    return find("userId", id).page(page - 1, 10).list();
  }

  @Override
  public Uni<CheckIn> findById(UUID checkInId) {
    return find("id", checkInId).firstResult();
  }

  @Override
  public Uni<CheckIn> findPreviousCheckIn(UUID userId, LocalDateTime date) {
    return find(
      "userId = :userId AND DATE(creationDate) = DATE(:date) AND status != :status",
      Parameters
        .with("userId", userId)
        .and("date", date)
        .and("status", Status.VALIDATED)
    ).firstResult();
  }

  @Override
  public Uni<CheckIn> create(CheckIn checkIn) {
    return persist(checkIn);
  }
}