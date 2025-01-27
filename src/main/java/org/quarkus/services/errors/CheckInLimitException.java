package org.quarkus.services.errors;

public class CheckInLimitException extends RuntimeException {
  public CheckInLimitException(String msg) {
    super(msg);
  }
}
