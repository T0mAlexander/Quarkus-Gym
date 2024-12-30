package org.quarkus.services.errors;

@SuppressWarnings("ALL")
public class UserExistsException extends RuntimeException {
  public UserExistsException(String msg) {
    super(msg);
  }
}