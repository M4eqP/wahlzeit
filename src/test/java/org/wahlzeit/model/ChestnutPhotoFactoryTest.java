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

public class ChestnutPhotoFactoryTest {
    @Test
    public void testConstructor() {
        // there's no properties we could validate, but we can at least ensure the class can be instantiated
        new ChestnutPhotoFactory();
    }

    /**
     * Create empty photo
     */
    /*
      Instantiation of new Photo fails because
        Caused by: java.lang.NullPointerException: No API environment is registered for this thread.
    @Test
    public void testCreatePhoto() {
        PhotoFactory factory = new ChestnutPhotoFactory();
        Photo photo = factory.createPhoto();
        assert photo instanceof ChestnutPhoto;
    }
    */
}
