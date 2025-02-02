package org.quarkus.algorithms;

import org.locationtech.jts.geom.Point;

/**
 * Coordenadas geográficas.
 * <p>
 * Esta classe define as coordenadas geográficas, incluindo latitude e longitude.
 * </p>
 */

public record Coordinates(double latitude, double longitude) {
  public Coordinates(Point location) {
    this(location.getY(), location.getX());
  }
}