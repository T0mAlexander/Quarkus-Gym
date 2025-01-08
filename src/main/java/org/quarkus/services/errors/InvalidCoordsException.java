package org.quarkus.services.errors;

public class InvalidCoordsException extends RuntimeException {
  public InvalidCoordsException(String msg) {
    super(msg);
  }
}
