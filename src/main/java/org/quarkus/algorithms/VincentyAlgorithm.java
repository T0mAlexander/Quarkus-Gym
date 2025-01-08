package org.quarkus.algorithms;

// Referência matemática: https://en.wikipedia.org/wiki/Vincenty%27s_formulae

/**
 * Algoritmo de cálculo de distâncias baseado na fórmula do matemático polaco-americano Thaddeus Vincenty publicada em 1975 que possui margem de precisão entre 10 centímetros até 0.5 milímetros
 */

@SuppressWarnings("unused")
public class VincentyAlgorithm {
  public static double distanceBetweenCoordinates(Coordinates from, Coordinates to) {
    final double semiMajorAxis = 6378137.0; // Raio equatorial da Terra (unidade: metros)
    final double flattening = 1 / 298.257223563; // Diferença entre o raio equatorial e polar dividido pelo equatorial
    final double semiMinorAxis = (1 - flattening) * semiMajorAxis;

    double longitudeDifference = toRadian(to.longitude() - from.longitude());
    double reducedLatitudeFrom = Math.atan((1 - flattening) * Math.tan(toRadian(from.latitude())));
    double reducedLatitudeTo = Math.atan((1 - flattening) * Math.tan(toRadian(to.latitude())));

    double sinReducedLatitudeFrom = Math.sin(reducedLatitudeFrom);
    double cosReducedLatitudeFrom = Math.cos(reducedLatitudeFrom);
    double sinReducedLatitudeTo = Math.sin(reducedLatitudeTo);
    double cosReducedLatitudeTo = Math.cos(reducedLatitudeTo);

    double lambda = longitudeDifference;
    double previousLambda;
    int iterationLimit = 100;
    double cosSquaredAlpha;
    double sinSigma;
    double cos2SigmaM;
    double cosSigma;
    double sigma;

    do {
      double sinLambda = Math.sin(lambda);
      double cosLambda = Math.cos(lambda);
      double term = cosReducedLatitudeFrom * sinReducedLatitudeTo - sinReducedLatitudeFrom * cosReducedLatitudeTo * cosLambda;
      sinSigma = Math.sqrt((cosReducedLatitudeTo * sinLambda) * (cosReducedLatitudeTo * sinLambda) + term * term);

      if (sinSigma == 0) {
        return 0; // Pontos coincidentes
      }

      cosSigma = sinReducedLatitudeFrom * sinReducedLatitudeTo + cosReducedLatitudeFrom * cosReducedLatitudeTo * cosLambda;
      sigma = Math.atan2(sinSigma, cosSigma);
      double sinAlpha = cosReducedLatitudeFrom * cosReducedLatitudeTo * sinLambda / sinSigma;
      cosSquaredAlpha = 1 - sinAlpha * sinAlpha;
      cos2SigmaM = cosSigma - 2 * sinReducedLatitudeFrom * sinReducedLatitudeTo / cosSquaredAlpha;

      if (Double.isNaN(cos2SigmaM)) {
        cos2SigmaM = 0; // Linha do Equador: cosSquaredAlpha=0
      }

      double C = flattening / 16 * cosSquaredAlpha * (4 + flattening * (4 - 3 * cosSquaredAlpha));
      previousLambda = lambda;
      lambda = longitudeDifference + (1 - C) * flattening * sinAlpha * (
        sigma + C * sinSigma * (
          cos2SigmaM + C * cosSigma * (
            -1 + 2 * cos2SigmaM * cos2SigmaM
          )
        )
      );
    } while (Math.abs(lambda - previousLambda) > 1e-12 && --iterationLimit > 0);

    if (iterationLimit == 0) {
      return Double.NaN; // Formula failed to converge
    }

    double uSquared = cosSquaredAlpha * (semiMajorAxis * semiMajorAxis - semiMinorAxis * semiMinorAxis) / (semiMinorAxis * semiMinorAxis);
    double A = 1 + uSquared / 16384 * (4096 + uSquared * (-768 + uSquared * (320 - 175 * uSquared)));
    double B = uSquared / 1024 * (256 + uSquared * (-128 + uSquared * (74 - 47 * uSquared)));
    double deltaSigma = B * sinSigma * (
      cos2SigmaM + B / 4 * (
        cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
          B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)
      )
    );

    return semiMinorAxis * A * (sigma - deltaSigma); // Unidade no SI: metros (m)
  }

  private static double toRadian(double degree) {
    return degree * (Math.PI / 180);
  }
}