package org.quarkus.services.errors;

public class InvalidGymSearch extends RuntimeException {
  public InvalidGymSearch(String msg) {
    super(msg);
  }
}
