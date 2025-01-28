package org.quarkus.services.errors;

public class EmptyCheckInHistoryException extends RuntimeException {
  public EmptyCheckInHistoryException(String msg) {
    super(msg);
  }
}
