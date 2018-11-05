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
