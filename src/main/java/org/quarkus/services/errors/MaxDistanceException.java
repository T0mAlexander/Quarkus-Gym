package org.quarkus.services.errors;

public class MaxDistanceException extends RuntimeException {
  public MaxDistanceException(String msg) {
    super(msg);
  }
}
