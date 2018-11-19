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
public class CartesianCoordinate implements Coordinate {
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
    public CartesianCoordinate(double x, double y, double z) {
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
     * @param other another CartesianCoordinate
     * @return if other CartesianCoordinate is equal with this one
     */
    public boolean isEqual(Coordinate other) {
        // need a CartesianCoordinate to be able to access x, y, z values
        // also, the function call will implicitly reinterpret the original values into Cartesian coordinates
        CartesianCoordinate cartesianOther = other.asCartesianCoordinate();

        return this.x == cartesianOther.x && this.y == cartesianOther.y && this.z == cartesianOther.z;
    }

    /**
     * @methodtype compare
     * Checks whether two coordinates are equal.
     * @param other another CartesianCoordinate
     * @return true if both are equal, false otherwise
     */
    public boolean equals(Coordinate other) {
        return isEqual(other);
    }

    /**
     * Calculates direct cartesian distance between two cartesian coordinates.
     * Will convert other coordinate automatically, if necessary.
     *
     * @methodytpe helper
     * @param other another Coordinate
     * @return distance between current and other coordinate
     */
    public double getCartesianDistance(Coordinate other) {
        // need a CartesianCoordinate to be able to access x, y, z values
        // also, the function call will implicitly reinterpret the original values into Cartesian coordinates
        CartesianCoordinate cartesianOther = other.asCartesianCoordinate();

        double xDiff = Math.abs(cartesianOther.x - this.x);
        double yDiff = Math.abs(cartesianOther.y - this.y);
        double zDiff = Math.abs(cartesianOther.z - this.z);

        return Math.abs(Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2)));
    }

    /**
     * "Convert" current instance into CartesianCoordinate
     *
     * @methodtype conversion
     */
    public CartesianCoordinate asCartesianCoordinate() {
        // As we are in the CartesianCoordinate class body, we can just return the current instance
        return this;
    }

    /**
     * "Convert" current instance into SphericCoordinate
     *
     * @methodtype conversion
     */
    public SphericCoordinate asSphericCoordinate() {
        // calculate radius, phi and theta values from own values
        double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        double theta = Math.acos(z / radius);
        double phi = Math.atan2(y, x);

        // use these values to construct new SphericCoordinate
        return new SphericCoordinate(phi, theta, radius);
    }
}
