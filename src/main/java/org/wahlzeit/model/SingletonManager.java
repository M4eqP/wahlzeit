/*
 * Copyright (c) 2018 by M4eqP <M4eqP@users.noreply.github.com>
 *
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

import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.PatternInstance;

import java.util.logging.Logger;

/**
 * Manages all singletons in package.
 *
 * Allows for managing all instances of singleton-like classes in a single location.
 * Also allows unit tests to create several instances of singleton-like classes, while external users have to use the
 * getXXXinstance() methods of this class to create and get instances.
 */
@PatternInstance(
    name = "Singleton",
    participants = {
        "Singleton",
    }
)
@PatternInstance(
    name = "FactoryMethod",
    participants = {
        "Creator"
    }
)
@PatternInstance(
	name = "DependencyInjection",
	participants = {
		"Injector"
	}
)
@PatternInstance(
	name = "Lazy",
	participants = {
		"Injector"
	}
)
public class SingletonManager {
    private static final Logger log = Logger.getLogger(SingletonManager.class.getName());

    // will be lazy-initialized on first use
    // this allows for setting a custom factory and injecting it into the photo manager
    private static PhotoFactory photoFactory = null;
    private static PhotoManager photoManager = null;

    public static PhotoManager getPhotoManager() {
        if (photoManager == null) {
            // using dependency injection to allow unit test to create new instances of the classes
            photoManager = new ChestnutPhotoManager(getPhotoFactory());
        }

        return photoManager;
    }

    public static PhotoFactory getPhotoFactory() {
        if (photoFactory == null) {
            log.config(LogBuilder.createSystemMessage().addAction("setting generic ChestnutPhotoFactory").toString());
            setPhotoFactory(new ChestnutPhotoFactory());
        }

        return photoFactory;
    }

    public static synchronized void setPhotoFactory(PhotoFactory factory) {
        if (photoFactory != null) {
            throw new IllegalStateException("attempt to initalize PhotoFactory twice");
        }

        log.config(LogBuilder.createSystemMessage().addAction("setting custom ChestnutPhotoFactory").toString());
        photoFactory = factory;
    }

    /**
     * @methodtype factory
     *
     * Explicitly initializes photo factory object.
     */
    public static void initPhotoFactory() {
        // getter initializes object, so we can just call it and discard the result
        getPhotoFactory();
    }
}
