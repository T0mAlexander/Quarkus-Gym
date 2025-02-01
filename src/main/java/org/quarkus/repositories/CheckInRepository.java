package org.quarkus.repositories;

import io.smallrye.mutiny.Uni;
import org.quarkus.models.CheckIn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CheckInRepository {
  Uni<List<CheckIn>> userHistory(UUID id, int page);
  Uni<CheckIn> findPreviousCheckIn(UUID userId, LocalDateTime date);
  Uni<CheckIn> create(CheckIn checkIn);
  Uni<CheckIn> findById(UUID checkInId);
}