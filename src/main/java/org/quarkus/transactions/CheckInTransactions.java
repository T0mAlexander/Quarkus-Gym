package org.quarkus.transactions;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.models.CheckIn;
import org.quarkus.repositories.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CheckInTransactions implements PanacheRepository<CheckIn>, CheckInRepository {

  @Override
  public Uni<List<CheckIn>> userHistory(UUID id, int page) {
    return find("userId", id).page(page - 1, 10).list();
  }

  @Override
  public Uni<CheckIn> findPreviousCheckIn(UUID userId, LocalDateTime date) {
    return find("userId = ?1 AND DATE(creationDate) = DATE(?2)", userId, date).firstResult();
  }

  @Override
  public Uni<Integer> checkInsCount(String userId) {
    return null;
  }

  @Override
  public Uni<CheckIn> findCheckIn(String userId) {
    return null;
  }

  @Override
  public Uni<CheckIn> create(CheckIn checkIn) {
    return persist(checkIn);
  }
}