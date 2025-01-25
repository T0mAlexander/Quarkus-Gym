package org.quarkus.algorithms;

import org.locationtech.jts.geom.Point;

public record Coordinates(double latitude, double longitude) {
  public Coordinates(Point location) {
    this(location.getY(), location.getX());
  }
}
