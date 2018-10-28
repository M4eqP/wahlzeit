package org.wahlzeit.model;

/**
 * Represents cartesian coordinate.
 */
public class Coordinate {
    // cartesian coordinates
    private final double x;
    private final double y;
    private final double z;

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    };

    /**
     * Returns coordinate's X value
     * @return X value
     */
    public double getX() {
        return x;
    }

    /**
     * Returns coordinate's Y value
     * @return Y value
     */
    public double getY() {
        return y;
    }

    /**
     * Returns coordinate's Z value
     * @return Z value
     */
    public double getZ() {
        return z;
    }

    /**
     * Checks whether two coordinates are equal.
     * @param other another Coordinate
     * @return if other Coordinate is equal with this one
     */
    public boolean isEqual(Coordinate other) {
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    /**
     * Checks whether two coordinates are equal.
     * @param other another Coordinate
     * @return true if both are equal, false otherwise
     */
    public boolean equals(Coordinate other) {
        return isEqual(other);
    }

    /**
     * Calculates direct cartesian distance between two coordinates.
     *
     * @param other another Coordinate
     * @return distance between current and other coordinate
     */
    public double getDistance(Coordinate other) {
        double xDiff = Math.abs(other.x - this.x);
        double yDiff = Math.abs(other.y - this.y);
        double zDiff = Math.abs(other.z - this.z);

        return Math.abs(Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2)));
    }
}
