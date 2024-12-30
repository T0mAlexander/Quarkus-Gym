package org.quarkus.services.errors;

@SuppressWarnings("ALL")
public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException(String msg) {
    super(msg);
  }
}
