package org.quarkus.repositories.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, String> {

  private static final WKTWriter writer = new WKTWriter();
  private static final WKTReader reader = new WKTReader();

  @Override
  public String convertToDatabaseColumn(Point point) {
    if (point == null) {
      return null;
    }

    return writer.write(point);
  }

  @Override
  public Point convertToEntityAttribute(String wkt) {
    if (wkt == null || wkt.isEmpty()) {
      return null;
    }

    try {
      return (Point) reader.read(wkt);
    } catch (ParseException error) {
      throw new RuntimeException("Falha ao converter WKT para Point!", error);
    }
  }
}