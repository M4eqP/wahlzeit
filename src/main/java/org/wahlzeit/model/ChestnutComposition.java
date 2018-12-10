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
 * Describes the composition of chestnuts on a picture.
 */
public class ChestnutComposition {
    /**
     * Describes the orientation of chestnuts towards the camera.
     *
     * RANDOM shall be used if they don't share the same orientation.
     */
    public enum Orientation {
        RANDOM,
        TOP,
        BOTTOM,
        FRONT,
        REAR,
    }

    private int chestnutCount;
    private Orientation orientation;

    /**
     * @methodtype assertation
     */
    protected void assertNotNull(Object o) {
        assert o != null;
    }

    /**
     * @methodtype assertation
     */
    protected void assertIsValidCount(int count) {
        assert count >= 0;
    }

    /**
     * @methodtype constructor
     */
    public ChestnutComposition() {
        chestnutCount = 0;
        orientation = Orientation.RANDOM;
    }

    /**
     * @methodtype constructor
     */
    public ChestnutComposition(int count, Orientation orientation) {
        // preconditions
        assertNotNull(orientation);
        assertIsValidCount(count);

        this.chestnutCount = count;
        this.orientation = orientation;
    }

    /**
     * @methodtype get
     */
    public int getChestnutCount() {
        return chestnutCount;
    }

    /**
     * @methodtype get
     */
    public Orientation getOrientation() {
        return orientation;
    }
}
