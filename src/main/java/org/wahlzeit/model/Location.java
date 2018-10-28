package org.wahlzeit.model;

public class Location {
    private final Coordinate coordinate;

    /**
     * Construct new Location from existing Coordinate.
     *
     * @param coordinate coordinate
     */
    public Location(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Construct new Location from X, Y and Z values.
     *
     * @param x coordinate's X value
     * @param y coordinate's Y value
     * @param z coordinate's Z value
     */
    public Location(double x, double y, double z) {
        this.coordinate = new Coordinate(x, y, z);
    }

    /**
     * Get coordinate of Location.
     *
     * @return this Location's coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }
}
