package org.quarkus.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, String> {

  private static final WKTReader reader = new WKTReader();
  private static final WKTWriter writer = new WKTWriter();

  @Override
  public String convertToDatabaseColumn(Point point) {
    if (point == null) {
      return null;
    }
    return writer.write(point);
  }

  @Override
  public Point convertToEntityAttribute(String wkt) {
    if (wkt == null || wkt.trim().isEmpty()) {
      return null;
    }

    try {
      return (Point) reader.read(wkt);
    } catch (ParseException e) {
      try {
        WKBReader wkbReader = new WKBReader();
        return (Point) wkbReader.read(WKBReader.hexToBytes(wkt));
      } catch (Exception error) {
        throw new RuntimeException("Falha ao converter WKT/WKB para Point. WKT/WKB inválido: " + wkt, error);
      }
    }
  }
}