package org.quarkus.factories;

import org.quarkus.services.checkin.CheckInCreationService;
import org.quarkus.services.checkin.CheckInHistoryService;
import org.quarkus.transactions.CheckInTransactions;
import org.quarkus.transactions.GymTransactions;

@SuppressWarnings("unused")
public class CheckInFactory {
  public CheckInCreationService createService(CheckInTransactions checkIn, GymTransactions gym) {
    return new CheckInCreationService(checkIn, gym);
  }

  public CheckInHistoryService historyService(CheckInTransactions userHistory) {
    return new CheckInHistoryService(userHistory);
  }
}