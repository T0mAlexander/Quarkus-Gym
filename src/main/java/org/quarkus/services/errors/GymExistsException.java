package org.quarkus.services.errors;

public class GymExistsException extends RuntimeException {
  public GymExistsException(String msg) {
    super(msg);
  }
}
