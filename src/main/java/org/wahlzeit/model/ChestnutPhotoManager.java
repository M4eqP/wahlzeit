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

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.PatternInstance;

import java.util.ArrayList;
import java.util.Collection;

@PatternInstance(
	name = "FactoryMethod",
	participants = {
		"Product"
	}
)
@PatternInstance(
	name = "DependencyInjection",
	participants = {
		"Client"
	}
)
public class ChestnutPhotoManager extends PhotoManager {
    /**
     * @methodtype constructor
     */
    public ChestnutPhotoManager(PhotoFactory factory) {
        super(factory);
        photoTagCollector = factory.createPhotoTagCollector();

		assertClassInvariants();
    }

    /**
     * @methodtype factory
     */
    public Photo getPhotoFromId(PhotoId id) {
		assertClassInvariants();

        if (id == null) {
            return null;
        }

        Photo result = doGetPhotoFromId(id);

        if (result == null) {
            result = SingletonManager.getPhotoFactory().loadPhoto(id);
            if (result != null) {
                doAddPhoto(result);
            }
        }

		assertClassInvariants();

        return result;
    }

    /**
     * @methodtype command
     *
     * Load all persisted photos. Executed when Wahlzeit is restarted.
     */
    public void loadPhotos() {
		assertClassInvariants();

        Collection<ChestnutPhoto> existingPhotos = ObjectifyService.run(new Work<Collection<ChestnutPhoto>>() {
            @Override
            public Collection<ChestnutPhoto> run() {
                Collection<ChestnutPhoto> existingPhotos = new ArrayList<ChestnutPhoto>();
                readObjects(existingPhotos, ChestnutPhoto.class);
                return existingPhotos;
            }
        });

        for (ChestnutPhoto photo : existingPhotos) {
            if (!doHasPhoto(photo.getId())) {
                log.config(LogBuilder.createSystemMessage().
                        addParameter("Load Photo with ID", photo.getIdAsString()).toString());
                loadScaledImages(photo);
                doAddPhoto(photo);
            } else {
                log.config(LogBuilder.createSystemMessage().
                        addParameter("Already loaded Photo", photo.getIdAsString()).toString());
            }
        }

        log.info(LogBuilder.createSystemMessage().addMessage("All photos loaded.").toString());

		assertClassInvariants();
    }
}
