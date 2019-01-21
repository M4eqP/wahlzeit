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

import java.util.Iterator;

/**
 * Static manager class. Owns all instances of ChestnutCompositionType, and provides a factory for
 * ChestnutComposition.
 */
public class ChestnutCompositionManager {
    // root object of the type object hierarchy
    // stores all subtypes that might be produced
    // any orientation can be considered random, therefore initializing with this value and treating all other orientations as subtypes
    private static ChestnutCompositionType rootType = new ChestnutCompositionType(ChestnutCompositionType.Orientation.RANDOM);

    /**
     * Returns an existing type object if possible, or creates a new one and stores it for the future.
     *
     * @methodtype factory
     */
    private static ChestnutCompositionType getChestnutCompositionType(ChestnutCompositionType.Orientation orientation) {
        if (orientation == rootType.getOrientation())
            return rootType;

        // check if a similar type object was created already
        // the lookup is implemented using a linear search -> O(n) algorithm
        // not as efficient as it could be (O(log(n)) should be possible with a sorted container), but we just have 5
        // type objects at most and it's not really worth the effort
        ChestnutCompositionType existingType = null;
        for (Iterator<ChestnutCompositionType> iterator = rootType.getSubTypeIterator(); iterator.hasNext(); existingType = iterator.next()) {
            if (existingType.getOrientation() == orientation)
                return existingType;
        }

        // if not, create a new one, insert it in the object storage, and return it
        ChestnutCompositionType newType = new ChestnutCompositionType(orientation);
        rootType.addSubType(newType);
        return newType;
    }

    /**
     *
     *
     * @methodtype factory
     */
    public static ChestnutComposition getInstance(int count, ChestnutCompositionType.Orientation orientation) {
        ChestnutCompositionType type = getChestnutCompositionType(orientation);

        return new ChestnutComposition(type, count);
    }
}
