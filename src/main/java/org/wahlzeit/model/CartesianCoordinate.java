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

import org.wahlzeit.utils.PatternInstance;

/**
 * Represents cartesian coordinate.
 */
@PatternInstance(
    name = "ChainOfResponsibility",
    participants = {
        "Receiver"
    }
)
public class CartesianCoordinate extends AbstractCoordinate {
    // cartesian coordinates
    private final double x;
    private final double y;
    private final double z;

    /**
     * Checks whether object is in a valid state.
     *
     * @methodtype assertation
     */
    @Override
    protected void assertClassInvariants() {
        assertNotNaN(x);
        assertNotNaN(y);
        assertNotNaN(z);
    }

    /**
     * @methodtype constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    CartesianCoordinate(final double x, final double y, final double z) {
        // preconditions
        assertNotNaN(x);
        assertNotNaN(y);
        assertNotNaN(z);

        this.x = x;
        this.y = y;
        this.z = z;

        assertClassInvariants();
    };

    /**
     * @methodtype get
     * Returns coordinate's X value
     * @return X value
     */
    public double getX() {
        assertClassInvariants();

        return x;
    }

    /**
     * @methodtype get
     * Returns coordinate's Y value
     * @return Y value
     */
    public double getY() {
        assertClassInvariants();

        return y;
    }

    /**
     * @methodtype get
     * Returns coordinate's Z value
     * @return Z value
     */
    public double getZ() {
        assertClassInvariants();

        return z;
    }

    /**
     * "Convert" current instance into CartesianCoordinate
     *
     * @methodtype conversion
     */
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();

        // As we are in the CartesianCoordinate class body, we can just return the current instance
        return this;
    }

    /**
     * "Convert" current instance into SphericCoordinate
     *
     * @methodtype conversion
     */
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();

        // calculate radius, phi and theta values from own values
        double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        double theta = Math.acos(z / radius);
        double phi = Math.atan2(y, x);

        // use these values to construct new SphericCoordinate
        SphericCoordinate result = new SphericCoordinate(phi, theta, radius);

        // post conditions
        assertNotNaN(radius);
        assertNotNaN(theta);
        assertNotNaN(phi);

        assertClassInvariants();

        return result;
    }
}
