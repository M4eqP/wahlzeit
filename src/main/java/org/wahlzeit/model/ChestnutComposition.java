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
 *
 *
 * +-----------------+
 * | Object creation |
 * +-----------------+
 *
 * Method call tree:
 * -----------------
 *
 * ChestnutCompositionManager.getInstance(int count, ChestnutCompositionType.Orientation orientation)
 * -> type = ChestnutCompositionType.getChestnutCompositionType(orientation)
 * -> new ChestnutComposition(count, type)
 *
 *
 * Classification via object creation table:
 * -----------------------------------------
 *
 * 1. Delegation: separate-object
 * 2. Selection: by-subclassing
 * 3. Configuration: in-code
 * 4. Instantiation: by-class-object
 * 5. Initialization: default
 * 6. Building: default
 */
public class ChestnutComposition {
    private int chestnutCount;
    private ChestnutCompositionType type;

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
    protected void assertIsValidCount(int count) {
        assert count >= 0;
    }

    /**
     * @methodtype assertation
     */
    protected void assertClassInvariants() {
        assertNotNull(type);
        assertIsValidCount(chestnutCount);
    }

    /**
     * Package-local
     * @methodtype constructor
     */
    ChestnutComposition(ChestnutCompositionType type, int chestnutCount) {
        // preconditions
        assertNotNull(type);
        assertIsValidCount(chestnutCount);

        this.type = type;
        this.chestnutCount = chestnutCount;

        assertClassInvariants();
    }

    /**
     * @methodtype get
     */
    public int getChestnutCount() {
        assertClassInvariants();

        return chestnutCount;
    }

    /**
     * @methodtype get
     */
    public ChestnutCompositionType getType() {
        assertClassInvariants();

        return type;
    }
}
