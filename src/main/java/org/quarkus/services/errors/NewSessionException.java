package org.quarkus.services.errors;

public class NewSessionException extends RuntimeException {
  public NewSessionException(String msg) {
    super(msg);
  }
}