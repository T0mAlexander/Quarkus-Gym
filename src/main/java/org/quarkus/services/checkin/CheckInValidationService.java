package org.quarkus.services.checkin;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.panache.common.Parameters;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.models.CheckIn;
import org.quarkus.services.errors.InvalidCheckInException;
import org.quarkus.services.user.TokenService;
import org.quarkus.transactions.CheckInTransactions;
import org.quarkus.utils.checkin.Status;
import org.quarkus.utils.user.Role;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CheckInValidationService {
  private final CheckInTransactions service;
  private final TokenService jwt;

  @Inject
  public CheckInValidationService(CheckInTransactions service, TokenService jwt) {
    this.service = service;
    this.jwt = jwt;
  }

  @WithTransaction
  public Uni<CheckIn> validateCheckIn(UUID checkInId, String token) {
    return service.findById(checkInId)
      .onItem().ifNull().failWith(new InvalidCheckInException("Check-in inválido ou inexistente!"))
      .onItem().transformToUni(checkIn -> {
        if (checkIn.getStatus() == Status.VALIDATED) {
          return Uni.createFrom().failure(new InvalidCheckInException("Este check-in já foi validado!"));
        }

        if (Duration.between(checkIn.getCreationDate(), LocalDateTime.now()).toMinutes() >= 10) {
          checkIn.setStatus(Status.EXPIRED);
          return service.update("""
          UPDATE CheckIn
          SET status = :status
          WHERE id = :id
          """, Parameters.with("status", checkIn.getStatus()).and("id", checkIn.getId()))
            .replaceWith(checkIn)
            .onFailure().invoke(failure -> System.err.println("Erro ao expirar check-in: " + checkIn.getId()))
            .replaceWith(Uni.createFrom().failure(new InvalidCheckInException("Este check-in já expirou!")));
        }

        return jwt.checkRole(token).onItem().transformToUni(role -> {
          if (role != Role.ADMIN) {
            return Uni.createFrom().failure(new InvalidCheckInException("Usuário não autorizado!"));
          }

          checkIn.setStatus(Status.VALIDATED);
          checkIn.setValidationDate(LocalDateTime.now());

          return service.update("""
          UPDATE CheckIn
          SET status = :status, validationDate = :validationDate
          WHERE id = :id
          """, Parameters.with("status", checkIn.getStatus())
              .and("validationDate", checkIn.getValidationDate())
              .and("id", checkIn.getId()))
            .replaceWith(checkIn);
        });
      });
  }

  @WithTransaction
  @Scheduled(every = "5m")
  @SuppressWarnings("unused")
  public Uni<Void> verifyExpiration() {
    return service.findAll().list().onItem().transformToUni(checkIns -> {
      List<Uni<Void>> expiredCheckIns = checkIns.stream()
        .filter(checkIn -> checkIn.getStatus() == Status.CREATED &&
          Duration.between(checkIn.getCreationDate(), LocalDateTime.now()).toMinutes() >= 10)
        .map(checkIn -> {
          checkIn.setStatus(Status.EXPIRED);
          return service.update("""
        UPDATE CheckIn
        SET status = :status
        WHERE id = :id
        """, Parameters.with("status", checkIn.getStatus()).and("id", checkIn.getId()))
            .onFailure().invoke(failure -> System.err.println("Erro ao expirar check-in: " + checkIn.getId()))
            .replaceWithVoid();
        }).toList();

      if (expiredCheckIns.isEmpty()) {
        return Uni.createFrom().voidItem();
      }

      return Uni.combine().all().unis(expiredCheckIns).collectFailures().discardItems();
    });
  }

}