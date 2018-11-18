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

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class validates the default behavior of the class SingletonManager.
 */
public class SingletonManagerTest {
    @Test
    public void testGetPhotoFactory() {
        PhotoFactory factory = SingletonManager.getPhotoFactory();

        // we want an instance and not null
        assertNotEquals(factory, null);

        // also, we want to know whether the automatically created instance is of the derived type
        assertTrue(factory instanceof ChestnutPhotoFactory);
    }

    /*
      Initialization fails because:
        Caused by: java.lang.NullPointerException: No API environment is registered for this thread.

    @Test
    public void testGetPhotoManager() {
        PhotoManager manager = SingletonManager.getPhotoManager();

        // we want an instance and not null
        assertNotEquals(manager, null);

        // also, we want to know whether the instance is of the derived type
        assertTrue(manager instanceof ChestnutPhotoManager);
    }*/
}
