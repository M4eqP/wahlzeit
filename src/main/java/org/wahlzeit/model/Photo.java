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

import com.google.api.client.util.ArrayMap;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.ObjectManager;

import java.util.Map;

/**
 * A photo represents a user-provided (uploaded) photo.
 */
@Entity
public class Photo extends DataObject {

	/**
	 *
	 */
	public static final String IMAGE = "image";
	public static final String THUMB = "thumb";
	public static final String LINK = "link";
	public static final String PRAISE = "praise";
	public static final String NO_VOTES = "noVotes";
	public static final String CAPTION = "caption";
	public static final String DESCRIPTION = "description";
	public static final String KEYWORDS = "keywords";

	public static final String TAGS = "tags";
	public static final String OWNER_ID = "ownerId";

	public static final String STATUS = "status";
	public static final String IS_INVISIBLE = "isInvisible";
	public static final String UPLOADED_ON = "uploadedOn";

	/**
	 *
	 */
	public static final int MAX_PHOTO_WIDTH = 420;
	public static final int MAX_PHOTO_HEIGHT = 600;
	public static final int MAX_THUMB_PHOTO_WIDTH = 105;
	public static final int MAX_THUMB_PHOTO_HEIGHT = 150;

	protected PhotoId id = null;
	
	/**
	 *
	 */
	protected String ownerId;
	
	/**
	 * Each photo can be viewed in different sizes (XS, S, M, L, XL)
	 * Images are pre-computed in these sizes to optimize bandwidth when requested.
	 */
	@Ignore
	transient protected Map<PhotoSize, Image> images = new ArrayMap<PhotoSize, Image>();
	
	/**
	 *
	 */
	protected boolean ownerNotifyAboutPraise = false;
	protected EmailAddress ownerEmailAddress = EmailAddress.EMPTY;
	protected Language ownerLanguage = Language.ENGLISH;
	
	/**
	 *
	 */
	protected int width;
	protected int height;
	protected PhotoSize maxPhotoSize = PhotoSize.MEDIUM; // derived
	
	/**
	 *
	 */
	protected Tags tags = Tags.EMPTY_TAGS;
	
	/**
	 *
	 */
	protected PhotoStatus status = PhotoStatus.VISIBLE;
	
	/**
	 *
	 */
	protected int praiseSum = 10;
	protected int noVotes = 1;
	protected int noVotesAtLastNotification = 1;
	
	/**
	 *
	 */
	protected long creationTime = System.currentTimeMillis();
	
	/**
	 * The default type is jpg
	 */
	protected String ending = "jpg";

	/**
	 *
	 */
	protected Location location = null;
	
	/**
	 *
	 */
	//TODO: change it to a single long
	@Id
	Long idLong;
	@Parent
	Key parent = ObjectManager.applicationRootKey;

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
	protected void assertIsValidPraiseSum(int sum) {
		if (sum <= 0 || sum > 10)
			throw new IllegalArgumentException("praise sum must be 0 < sum <= 10");
	}

	/**
	 * @methodtype assertation
	 */
	protected void assertIsValidWidthOrHeight(int i) {
		if (i < 0)
			throw new IllegalArgumentException("width or height may not be negative");
	}

	/**
	 * @methodtype assertation
	 */
	protected void assertClassInvariants() {
		assertNotNull(id);
		assertNotNull(ownerId);
		assertNotNull(ownerEmailAddress);
		assertNotNull(ownerLanguage);
		assertNotNull(ending);

		assertIsValidPraiseSum(praiseSum);

		assertIsValidWidthOrHeight(width);
		assertIsValidWidthOrHeight(height);
	}

	/**
	 *
	 */
	public Photo() {
		id = PhotoId.getNextId();
		incWriteCount();
	}

	/**
	 * @methodtype constructor
	 */
	public Photo(PhotoId myId) {
		// preconditions
		assertNotNull(myId);

		id = myId;

		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype get
	 */
	public Image getImage(PhotoSize photoSize) {
		assertClassInvariants();

		// preconditions
		assertNotNull(photoSize);

		return images.get(photoSize);
	}

	/**
	 * @methodtype set
	 */
	public void setImage(PhotoSize photoSize, Image image) {
		assertClassInvariants();

		// preconditions
		assertNotNull(photoSize);
		assertNotNull(image);

		this.images.put(photoSize, image);

		assertClassInvariants();
	}

	/**
	 * @methodtype get
	 */
	public String getIdAsString() {
		assertClassInvariants();

		return id.asString();
	}

	/**
	 * @methodtype get
	 */
	public PhotoId getId() {
		assertClassInvariants();

		return id;
	}

	/**
	 * @methodtype get
	 */
	public String getOwnerId() {
		assertClassInvariants();

		return ownerId;
	}

	/**
	 * @methodtype set
	 */
	public void setOwnerId(String newName) {
		assertClassInvariants();

		// preconditions
		assertNotNull(newName);

		ownerId = newName;
		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype get
	 */
	public String getSummary(ModelConfig cfg) {
		assertClassInvariants();

		// preconditions
		assertNotNull(cfg);

		return cfg.asPhotoSummary(ownerId);
	}

	/**
	 * @methodtype get
	 */
	public String getCaption(ModelConfig cfg) {
		assertClassInvariants();

		// preconditions
		assertNotNull(cfg);

		String ownerName = UserManager.getInstance().getUserById(ownerId).getNickName();
		return cfg.asPhotoCaption(ownerName);
	}

	/**
	 * @methodtype get
	 */
	public boolean getOwnerNotifyAboutPraise() {
		assertClassInvariants();

		return ownerNotifyAboutPraise;
	}

	/**
	 * @methodtype set
	 */
	public void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise) {
		assertClassInvariants();

		ownerNotifyAboutPraise = newNotifyAboutPraise;
		incWriteCount();
	}

	/**
	 *
	 */
	public Language getOwnerLanguage() {
		assertClassInvariants();

		return ownerLanguage;
	}

	/**
	 *
	 */
	public void setOwnerLanguage(Language newLanguage) {
		assertClassInvariants();

		// preconditions
		assertNotNull(newLanguage);

		ownerLanguage = newLanguage;
		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean hasSameOwner(Photo photo) {
		assertClassInvariants();

		return photo.getOwnerEmailAddress().equals(ownerEmailAddress);
	}

	/**
	 * @methodtype get
	 */
	public EmailAddress getOwnerEmailAddress() {
		assertClassInvariants();

		return ownerEmailAddress;
	}

	/**
	 * @methodtype set
	 */
	public void setOwnerEmailAddress(EmailAddress newEmailAddress) {
		assertClassInvariants();

		// preconditions
		assertNotNull(newEmailAddress);

		ownerEmailAddress = newEmailAddress;
		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype get
	 */
	public int getWidth() {
		assertClassInvariants();

		return width;
	}

	/**
	 * @methodtype get
	 */
	public int getHeight() {
		assertClassInvariants();

		return height;
	}

	/**
	 * @methodtype get
	 */
	public int getThumbWidth() {
		assertClassInvariants();

		return isWiderThanHigher() ? MAX_THUMB_PHOTO_WIDTH : (width * MAX_THUMB_PHOTO_HEIGHT / height);
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean isWiderThanHigher() {
		assertClassInvariants();

		return (height * MAX_PHOTO_WIDTH) < (width * MAX_PHOTO_HEIGHT);
	}

	/**
	 * @methodtype get
	 */
	public int getThumbHeight() {
		assertClassInvariants();

		return isWiderThanHigher() ? (height * MAX_THUMB_PHOTO_WIDTH / width) : MAX_THUMB_PHOTO_HEIGHT;
	}

	/**
	 * @methodtype set
	 */
	public void setWidthAndHeight(int newWidth, int newHeight) {
		assertClassInvariants();

		width = newWidth;
		height = newHeight;

		maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * Can this photo satisfy provided photo size?
	 *
	 * @methodtype boolean-query
	 */
	public boolean hasPhotoSize(PhotoSize size) {
		assertClassInvariants();

		// preconditions
		assertNotNull(size);

		return maxPhotoSize.asInt() >= size.asInt();
	}

	/**
	 * @methodtype get
	 */
	public PhotoSize getMaxPhotoSize() {
		assertClassInvariants();

		return maxPhotoSize;
	}

	/**
	 * @methodtype get
	 */
	public String getPraiseAsString(ModelConfig cfg) {
		assertClassInvariants();

		// preconditions
		assertNotNull(cfg);

		return cfg.asPraiseString(getPraise());
	}

	/**
	 * @methodtype get
	 */
	public double getPraise() {
		assertClassInvariants();

		return (double) praiseSum / noVotes;
	}

	/**
	 *
	 */
	public void addToPraise(int value) {
		assertClassInvariants();

		int newPraiseSum = praiseSum + value;

		// preconditions
		assertIsValidPraiseSum(newPraiseSum);

		praiseSum = newPraiseSum;

		noVotes += 1;
		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean isVisible() {
		assertClassInvariants();

		return status.isDisplayable();
	}

	/**
	 * @methodtype get
	 */
	public PhotoStatus getStatus() {
		assertClassInvariants();

		return status;
	}

	/**
	 * @methodtype set
	 */
	public void setStatus(PhotoStatus newStatus) {
		assertClassInvariants();

		// preconditions
		assertNotNull(newStatus);

		status = newStatus;
		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean hasTag(String tag) {
		assertClassInvariants();

		// preconditions
		assertNotNull(tag);

		return tags.hasTag(tag);
	}

	/**
	 * @methodtype get
	 */
	public Tags getTags() {
		assertClassInvariants();

		return tags;
	}

	/**
	 * @methodtype set
	 */
	public void setTags(Tags newTags) {
		assertClassInvariants();

		// preconditions
		assertNotNull(newTags);

		tags = newTags;
		incWriteCount();

		assertClassInvariants();
	}

	/**
	 * @methodtype get
	 */
	public long getCreationTime() {
		assertClassInvariants();

		return creationTime;
	}


	public String getEnding() {
		assertClassInvariants();

		return ending;
	}

	public void setEnding(String ending) {
		assertClassInvariants();

		// preconditions
		assertNotNull(ending);

		this.ending = ending;

		assertClassInvariants();
	}

	/**
	 * @methodtype boolean query
	 */
	public boolean hasNewPraise() {
		assertClassInvariants();

		return noVotes > noVotesAtLastNotification;
	}

	/**
	 * @methodtype set
	 */
	public void setNoNewPraise() {
		assertClassInvariants();

		noVotesAtLastNotification = noVotes;
		incWriteCount();
	}

	/**
	 * @methodtype get
	 */
	public Location getLocation() {
		assertClassInvariants();

		return this.location;
	}

	/**
	 * @methodtype set
	 */
	public void setLocation(Location location) {
		assertClassInvariants();

		// preconditions
		assertNotNull(location);

		this.location = location;
	}
}
