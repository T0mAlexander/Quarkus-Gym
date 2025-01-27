package org.quarkus.services.errors;

public class InvalidCheckInException extends RuntimeException {
  public InvalidCheckInException(String msg) {
    super(msg);
  }
}
