package org.quarkus.services.errors;

public class GymNotFoundException extends RuntimeException {
  public GymNotFoundException(String msg) {
    super(msg);
  }
}
