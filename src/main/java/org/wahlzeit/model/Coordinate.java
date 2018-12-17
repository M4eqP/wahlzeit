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
 * Common interface to coordinates of all kinds (Cartesian, spheric, ...)
 */
interface Coordinate {
    /**
     * @methodtype conversion
     */
    CartesianCoordinate asCartesianCoordinate();

    /**
     * @methodtype conversion
     */
    SphericCoordinate asSphericCoordinate();

    /**
     * @methodtype helper
     */
    double getCartesianDistance(Coordinate other);

    /**
     * @methodtype helper
     */
    double getCentralAngle(Coordinate other);

    /**
     * @methodtype boolean-query
     */
    boolean isEqual(Coordinate other);

    /**
     * @methodtype boolean-query
     */
    boolean equals(Object other);

    /**
     * @methodtype factory
     */
    static CartesianCoordinate getCartesianCoordinate(final int x, final int y, final int z) {
        return CoordinateService.getCartesianCoordinate(x, y, z);
    }

    /**
     * @methodtype factory
     */
    static SphericCoordinate getSphericCoordinate(final double phi, final double theta, final double radius) {
        return CoordinateService.getSphericCoordinate(phi, theta, radius);
    }
}