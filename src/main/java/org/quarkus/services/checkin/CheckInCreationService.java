package org.quarkus.services.checkin;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus.algorithms.Coordinates;
import org.quarkus.algorithms.VincentyAlgorithm;
import org.quarkus.models.CheckIn;
import org.quarkus.services.errors.CheckInLimitException;
import org.quarkus.services.errors.GymNotFoundException;
import org.quarkus.services.errors.MaxDistanceException;
import org.quarkus.transactions.CheckInTransactions;
import org.quarkus.transactions.GymTransactions;
import org.quarkus.utils.checkin.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
@SuppressWarnings("unused")
public class CheckInCreationService {
  private final CheckInTransactions service;
  private final GymTransactions gym;

  @Inject
  public CheckInCreationService(CheckInTransactions service, GymTransactions gym) {
    this.service = service;
    this.gym = gym;
  }

  @WithTransaction
  @CacheInvalidate(cacheName = "checkIns")
  public Uni<CheckIn> createCheckIn(UUID userId, UUID gymId, double userLatitude, double userLongitude) {
    return gym.findById(gymId).onItem().ifNull().failWith(new GymNotFoundException("Esta academia não existe!")).onItem().transformToUni(foundGym -> {

      double userDistance = VincentyAlgorithm.calculateDistance(
        new Coordinates(userLatitude, userLongitude),
        new Coordinates(
          foundGym.getLocation().getY(),
          foundGym.getLocation().getX())
      );

      double maxCheckInDistance = 250.0; // 250 metros

      if (userDistance > maxCheckInDistance) {
        return Uni.createFrom()
          .failure(new MaxDistanceException("Você está distante da academia para realizar o check-in!"));
      }

      return service.findPreviousCheckIn(
        userId, LocalDateTime.now()
      ).onItem().transformToUni(previousCheckIn -> {
        if (previousCheckIn != null && previousCheckIn.getStatus() != Status.EXPIRED) {
          return Uni.createFrom().failure(new CheckInLimitException("Limite diário de check-in atingido nesta academia!"));
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setGymId(gymId);
        checkIn.setUserId(userId);
        checkIn.setCreationDate(LocalDateTime.now());
        checkIn.setStatus(Status.CREATED);
        checkIn.setGym(foundGym);

        return service.create(checkIn);
      });
    });
  }
}