package org.quarkus.factories;

import org.quarkus.services.checkin.CheckInCreationService;
import org.quarkus.services.checkin.CheckInHistoryService;
import org.quarkus.services.checkin.CheckInValidationService;
import org.quarkus.services.user.TokenService;
import org.quarkus.transactions.CheckInTransactions;
import org.quarkus.transactions.GymTransactions;

/**
 * Fábrica para criar instâncias dos serviços de check-in.
 * <p>
 * Esta fábrica fornece métodos para criar serviços relacionados a check-ins, incluindo
 * criação de check-ins, histórico de check-ins e validação de check-ins.
 * </p>
 */

@SuppressWarnings("unused")
public class CheckInFactory {

  /**
   * Cria uma instância de {@link CheckInCreationService}.
   *
   * @param checkIn Transações de check-in.
   * @param gym Transações de academia.
   * @return Uma nova instância de {@link CheckInCreationService}.
   */
  public CheckInCreationService createService(CheckInTransactions checkIn, GymTransactions gym) {
    return new CheckInCreationService(checkIn, gym);
  }

  /**
   * Cria uma instância de {@link CheckInHistoryService}.
   *
   * @param userHistory Histórico de transações de check-in do usuário.
   * @return Uma nova instância de {@link CheckInHistoryService}.
   */
  public CheckInHistoryService historyService(CheckInTransactions userHistory) {
    return new CheckInHistoryService(userHistory);
  }

  /**
   * Cria uma instância de {@link CheckInValidationService}.
   *
   * @param validateCheckIn Transações de validação de check-in.
   * @param jwt Serviço de token JWT.
   * @return Uma nova instância de {@link CheckInValidationService}.
   */
  public CheckInValidationService validationService(CheckInTransactions validateCheckIn, TokenService jwt) {
    return new CheckInValidationService(validateCheckIn, jwt);
  }
}