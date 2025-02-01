package org.quarkus.factories;

import org.quarkus.services.checkin.CheckInCreationService;
import org.quarkus.services.checkin.CheckInHistoryService;
import org.quarkus.services.checkin.CheckInValidationService;
import org.quarkus.services.user.TokenService;
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

  public CheckInValidationService validationService(CheckInTransactions validateCheckIn, TokenService jwt) {
    return new CheckInValidationService(validateCheckIn, jwt);
  }
}