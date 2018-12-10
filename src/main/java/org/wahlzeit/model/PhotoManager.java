/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
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

import com.google.appengine.api.images.Image;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.wahlzeit.model.persistence.ImageStorage;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A photo manager provides access to and manages photos.
 */
public class PhotoManager extends ObjectManager {

	// injected through constructor
	protected final PhotoFactory photoFactory;

	protected static final Logger log = Logger.getLogger(PhotoManager.class.getName());

	/**
	 * In-memory cache for photos
	 */
	protected Map<PhotoId, Photo> photoCache = new HashMap<PhotoId, Photo>();

	/**
	 *
	 */
	protected PhotoTagCollector photoTagCollector = null;

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
		assertNotNull(photoFactory);
		assertNotNull(photoTagCollector);
	}

	/**
	 * @methodtype constructor
	 *
	 * package-local constructor to force users to use the SingletonManager
	 *
	 * using dependency injection to allow unit test to create new instances of PhotoManager and PhotoFactory
	 */
	PhotoManager(PhotoFactory factory) {
		try {
			assertNotNull(factory);
		} catch (AssertionError e) {
			throw new IllegalArgumentException("factory must not be null");
		}

		photoFactory = factory;
		photoTagCollector = factory.createPhotoTagCollector();

		assertClassInvariants();
	}

	/**
	 * @methodtype boolean-query
	 */
	public final boolean hasPhoto(String id) {
		assertClassInvariants();

		// preconditions
		assertNotNull(id);

		return hasPhoto(PhotoId.getIdFromString(id));
	}

	/**
	 * @methodtype boolean-query
	 */
	public final boolean hasPhoto(PhotoId id) {
		assertClassInvariants();

		return getPhoto(id) != null;
	}

	/**
	 * @methodtype factory
	 */
	public final Photo getPhoto(PhotoId id) {
		assertClassInvariants();

		return getPhotoFromId(id);
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
			result = photoFactory.loadPhoto(id);
			if (result != null) {
				doAddPhoto(result);
			}
		}

		return result;
	}

	/**
	 * @methodtype get
	 * @methodproperties primitive
	 */
	protected Photo doGetPhotoFromId(PhotoId id) {
		assertClassInvariants();

		return photoCache.get(id);
	}

	/**
	 * @methodtype command
	 * @methodproperties primitive
	 */
	protected void doAddPhoto(Photo myPhoto) {
		assertClassInvariants();

		photoCache.put(myPhoto.getId(), myPhoto);
	}

	/**
	 * @methodtype get
	 */
	public final Photo getPhoto(String id) {
		assertClassInvariants();

		return getPhoto(PhotoId.getIdFromString(id));
	}

	/**
	 * @methodtype init Loads all Photos from the Datastore and holds them in the cache
	 */
	public void init() {
		assertClassInvariants();

		loadPhotos();

		assertClassInvariants();
	}

	/**
	 * @methodtype command
	 *
	 * Load all persisted photos. Executed when Wahlzeit is restarted.
	 */
	public void loadPhotos() {
		assertClassInvariants();

		Collection<Photo> existingPhotos = ObjectifyService.run(new Work<Collection<Photo>>() {
			@Override
			public Collection<Photo> run() {
				Collection<Photo> existingPhotos = new ArrayList<Photo>();
				readObjects(existingPhotos, Photo.class);
				return existingPhotos;
			}
		});

		for (Photo photo : existingPhotos) {
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

	/**
	 * @methodtype boolean-query
	 * @methodproperty primitive
	 */
	protected boolean doHasPhoto(PhotoId id) {
		assertClassInvariants();

		return photoCache.containsKey(id);
	}

	/**
	 * @methodtype command
	 *
	 * Loads all scaled Images of this Photo from Google Cloud Storage
	 */
	protected void loadScaledImages(Photo photo) {
		assertClassInvariants();

		String photoIdAsString = photo.getId().asString();
		ImageStorage imageStorage = ImageStorage.getInstance();

		for (PhotoSize photoSize : PhotoSize.values()) {
			log.config(LogBuilder.createSystemMessage().
					addAction("loading image").
					addParameter("image size", photoSize.asString()).
					addParameter("photo ID", photoIdAsString).toString());
			if (imageStorage.doesImageExist(photoIdAsString, photoSize.asInt())) {
				try {
					Serializable rawImage = imageStorage.readImage(photoIdAsString, photoSize.asInt());
					if (rawImage != null && rawImage instanceof Image) {
						photo.setImage(photoSize, (Image) rawImage);
					}
				} catch (IOException e) {
					log.warning(LogBuilder.createSystemMessage().
							addParameter("size", photoSize.asString()).
							addParameter("photo ID", photoIdAsString).
							addException("Could not load image although it exists", e).toString());
				}
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Size does not exist", photoSize.asString()).toString());
			}
		}

		assertClassInvariants();
	}

	/**
	 *
	 */
	public void savePhoto(Photo photo) {
		assertClassInvariants();

		updateObject(photo);

		assertClassInvariants();
	}

	@Override
	protected void updateDependents(Persistent obj) {
		if (obj instanceof Photo) {
			Photo photo = (Photo) obj;
			saveScaledImages(photo);
			updateTags(photo);
			UserManager userManager = UserManager.getInstance();
			Client owner = userManager.getClientById(photo.getOwnerId());
			userManager.saveClient(owner);
		}
	}

	/**
	 * @methodtype helper
	 */
	public List<Tag> addTagsThatMatchCondition(List<Tag> tags, String condition) {
		assertClassInvariants();

		readObjects(tags, Tag.class, Tag.TEXT, condition);

		assertClassInvariants();

		return tags;
	}

	/**
	 * @methodtype command
	 *
	 * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
	 * the Datastore, it is simply not persisted.
	 */
	protected void saveScaledImages(Photo photo) {
		assertClassInvariants();

		String photoIdAsString = photo.getId().asString();
		ImageStorage imageStorage = ImageStorage.getInstance();
		PhotoSize photoSize;
		int it = 0;
		boolean moreSizesExist = true;
		do{
			photoSize = PhotoSize.values()[it];
			it++;
			Image image = photo.getImage(photoSize);
			if (image != null) {
				try {
					if (!imageStorage.doesImageExist(photoIdAsString, photoSize.asInt())) {
						imageStorage.writeImage(image, photoIdAsString, photoSize.asInt());
					}
				} catch (Exception e) {
					log.warning(LogBuilder.createSystemMessage().
							addException("Problem when storing image", e).toString());
					moreSizesExist = false;
				}
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("No image for size", photoSize.asString()).toString());
				moreSizesExist = false;
			}
		} while (it < PhotoSize.values().length && moreSizesExist);

		assertClassInvariants();
	}

	/**
	 * Removes all tags of the Photo (obj) in the datastore that have been removed by the user and adds all new tags of
	 * the photo to the datastore.
	 */
	protected void updateTags(Photo photo) {
		assertClassInvariants();

		// delete all existing tags, for the case that some have been removed
		deleteObjects(Tag.class, Tag.PHOTO_ID, photo.getId().asString());

		// add all current tags to the datastore
		Set<String> tags = new HashSet<String>();
		photoTagCollector.collect(tags, photo);
		for (Iterator<String> i = tags.iterator(); i.hasNext(); ) {
			Tag tag = new Tag(i.next(), photo.getId().asString());
			log.config(LogBuilder.createSystemMessage().addParameter("Writing Tag", tag.asString()).toString());
			writeObject(tag);
		}

		assertClassInvariants();
	}

	/**
	 *
	 */
	public void savePhotos() throws IOException{
		assertClassInvariants();

		updateObjects(photoCache.values());

		assertClassInvariants();
	}

	/**
	 * @methodtype get
	 */
	public Map<PhotoId, Photo> getPhotoCache() {
		assertClassInvariants();

		return photoCache;
	}

	/**
	 *
	 */
	public Set<Photo> findPhotosByOwner(String ownerName) {
		assertClassInvariants();

		Set<Photo> result = new HashSet<Photo>();
		readObjects(result, Photo.class, Photo.OWNER_ID, ownerName);

		for (Iterator<Photo> i = result.iterator(); i.hasNext(); ) {
			doAddPhoto(i.next());
		}

		return result;
	}

	/**
	 *
	 */
	public Photo getVisiblePhoto(PhotoFilter filter) {
		assertClassInvariants();

		filter.generateDisplayablePhotoIds();
		return getPhotoFromId(filter.getRandomDisplayablePhotoId());
	}

	/**
	 *
	 */
	public Photo createPhoto(String filename, Image uploadedImage) throws Exception {
		assertClassInvariants();

		PhotoId id = PhotoId.getNextId();
		Photo result = PhotoUtil.createPhoto(filename, id, uploadedImage);
		addPhoto(result);

		assertClassInvariants();

		return result;
	}

	/**
	 * @methodtype command
	 */
	public void addPhoto(Photo photo) throws IOException {
		assertClassInvariants();

		PhotoId id = photo.getId();
		assertIsNewPhoto(id);
		doAddPhoto(photo);

		GlobalsManager.getInstance().saveGlobals();

		assertClassInvariants();
	}

	/**
	 * @methodtype assertion
	 */
	protected void assertIsNewPhoto(PhotoId id) {
		if (hasPhoto(id)) {
			throw new IllegalStateException("Photo already exists!");
		}
	}

}
