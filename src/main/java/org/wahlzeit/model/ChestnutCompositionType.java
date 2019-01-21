/*
 * Copyright (c) 2019 M4eqP@users.noreply.github.com
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Type object corresponding to ChestnutComposition. Main property is the orientation of the chestnuts on the picture.
 */
public class ChestnutCompositionType {
    protected ChestnutCompositionType superType = null;
    protected Set<ChestnutCompositionType> subTypes = new HashSet<>();

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

    private Orientation orientation;

    /**
     * @methodtype assertation
     */
    protected void assertNotNull(Object o) {
        if (o == null)
            throw new NullPointerException("object must not be null");
    }

    /**
     * @methodtype assertation
     */
    protected void assertClassInvariants() {
        assertNotNull(orientation);
        assertNotNull(subTypes);
    }

    /**
     * Package-local constructor. Must not be used outside package.
     * @methodtype constructor
     */
    ChestnutCompositionType(Orientation orientation) {
        // preconditions
        assertNotNull(orientation);

        this.orientation = orientation;

        assertClassInvariants();
    }

    /**
     * @methodtype get
     */
    public Orientation getOrientation() {
        assertClassInvariants();

        return orientation;
    }

    /**
     * @methodtype get
     */
    public ChestnutCompositionType getSuperType() {
        assertClassInvariants();

        return superType;
    }

    /**
     * @methodtype set
     */
    public void setSuperType(ChestnutCompositionType superType) {
        // preconditions
        assertNotNull(superType);

        this.superType = superType;

        assertClassInvariants();
    }

    /**
     * @methodtype factory
     */
    public Iterator<ChestnutCompositionType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    /**
     * @methodtype command
     */
    public void addSubType(ChestnutCompositionType subType) {
        // preconditions
        assertNotNull(subType);

        subType.setSuperType(this);
        subTypes.add(subType);

        assertClassInvariants();
    }

    /**
     * @methodtype boolean-query
     */
    public boolean isSubtype(ChestnutCompositionType subType) {
        // preconditions
        assertNotNull(subType);

        boolean rv = subTypes.contains(subType);

        assertClassInvariants();

        return rv;
    }
}
