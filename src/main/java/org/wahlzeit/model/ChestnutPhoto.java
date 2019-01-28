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
 * Custom photo class, using the related other custom classes, and adding some design-by-contract checks.
 *
 *
 * +-----------------+
 * | Object creation |
 * +-----------------+
 *
 * Method call tree:
 * -----------------
 *
 * SingletonManager.getPhotoFactory()
 * -> ChestnutPhotoFactory.createPhoto(PhotoId)
 *    -> new ChestnutPhoto(id)
 *
 * Initialization of ChestnutPhotoFactory:
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * SingletonManager.getPhotoManager()
 * -> photoFactory = SingletonManager.getPhotoFactory()
 * -> new ChestnutPhotoManager(photoFactory)
 *    -> SingletonManager.getPhotoFactory()
 *
 *
 * Classification via object creation table:
 * -----------------------------------------
 *
 * 1. Delegation: separate-object
 * 2. Selection: by-subclassing
 * 3. Configuration: in-code
 * 4. Instantiation: in-code
 * 5. Initialization: by-fixed-signature
 * 6. Building: default
 */
public class ChestnutPhoto extends Photo {
    private ChestnutComposition composition = null;

    protected void assertSubClassInvariants() {
        assertNotNull(composition);
    }

    /**
     * @methodtype constructor
     */
    public ChestnutPhoto() {
        super();

        assertClassInvariants();
        assertSubClassInvariants();
    }

    /**
     * @methodtype constructor
     */
    public ChestnutPhoto(PhotoId id) {
        super(id);

        assertClassInvariants();
        assertSubClassInvariants();
    }

    /**
     * @methodtype get
     */
    public ChestnutComposition getComposition() {
        assertClassInvariants();
        assertSubClassInvariants();

        return composition;
    }

    /**
     * @methodtype set
     */
    public void setComposition(ChestnutComposition composition) {
        assertClassInvariants();
        assertSubClassInvariants();

        // preconditions
        assertNotNull(composition);

        this.composition = composition;

        assertClassInvariants();
        assertSubClassInvariants();
    }
}
