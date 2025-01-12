package org.quarkus.services.errors;

public class InvalidGymSearchException extends RuntimeException {
  public InvalidGymSearchException(String msg) {
    super(msg);
  }
}
