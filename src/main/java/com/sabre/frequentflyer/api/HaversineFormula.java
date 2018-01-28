package com.sabre.frequentflyer.api;

public class HaversineFormula {
    private static final int EARTH_RADIUS = 6371;

    /**
     * @param startLat  latitude of the first point
     * @param startLong longitude of the first point
     * @param endLat    latitude of the second point
     * @param endLong   latitude of the second point
     * @return distance between points casted to int
     * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine Formula</a>
     */
    public static int distance(double startLat, double startLong,
                               double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (EARTH_RADIUS * c);
    }

    /**
     * calculating sin^2 of given <code>val</code>/2
     * @param val value
     * @return sin^2(<code>val</code>/2)
     */
    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
