/*
 * Copyright (c) 2018 M4eqP@users.noreply.github.com
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

/**
 * Represents cartesian coordinate.
 */
public class Coordinate {
    // cartesian coordinates
    private final double x;
    private final double y;
    private final double z;

    /**
     * @methodtype constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    };

    /**
     * @methodtype get
     * Returns coordinate's X value
     * @return X value
     */
    public double getX() {
        return x;
    }

    /**
     * @methodtype get
     * Returns coordinate's Y value
     * @return Y value
     */
    public double getY() {
        return y;
    }

    /**
     * @methodtype get
     * Returns coordinate's Z value
     * @return Z value
     */
    public double getZ() {
        return z;
    }

    /**
     * @methodtype boolean-query
     * Checks whether two coordinates are equal.
     * @param other another Coordinate
     * @return if other Coordinate is equal with this one
     */
    public boolean isEqual(Coordinate other) {
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    /**
     * @methodtype compare
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
     * @methodytpe helper
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
