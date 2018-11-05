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

package org.wahlzeit.services;

import junit.framework.TestCase;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.Assert.assertNotEquals;

/**
 * Test cases for the EmailAddress class.
 */
public class EmailAddressTest extends TestCase {

	/**
	 *
	 */
	public EmailAddressTest(String name) {
		super(name);
	}

	/**
	 *
	 */
	public void testGetEmailAddressFromString() {
		// invalid email addresses are allowed for local testing and online avoided by Google

		assertTrue(createEmailAddressIgnoreException("bingo@bongo"));
		assertTrue(createEmailAddressIgnoreException("bingo@bongo.com"));
		assertTrue(createEmailAddressIgnoreException("bingo.bongo@bongo.com"));
		assertTrue(createEmailAddressIgnoreException("bingo+bongo@bango"));
	}

	/**
	 *
	 */
	protected boolean createEmailAddressIgnoreException(String ea) {
		try {
			EmailAddress.getFromString(ea);
			return true;
		} catch (IllegalArgumentException ex) {
			// creation failed
			return false;
		}
	}

	/**
	 *
	 */
	public void testEmptyEmailAddress() {
		assertFalse(EmailAddress.EMPTY.isValid());
	}

	/**
	 *
	 */
	public void testIsEqual() {
		final String addr0 = "test@test.com";
		final String addr1 = "test@test.net";
		final String addr2 = "admin@test.net";

		final EmailAddress ea0 = new EmailAddress(addr0);
		final EmailAddress ea1 = new EmailAddress(addr1);
		final EmailAddress ea2 = new EmailAddress(addr2);

		assertEquals(ea0, ea0);
		assertNotEquals(ea0, ea1);
		assertNotEquals(ea0, ea2);
		assertNotEquals(ea1, ea2);
	}

	/**
	 *
	 */
	public void testAsString() {
		final String addr0 = "test@test.com";
		final String addr1 = "test@test.net";
		final String addr2 = "admin@test.net";

		final EmailAddress ea0 = new EmailAddress(addr0);
		final EmailAddress ea1 = new EmailAddress(addr1);
		final EmailAddress ea2 = new EmailAddress(addr2);

		assertEquals(ea0.asString(), addr0);
		assertEquals(ea1.asString(), addr1);
		assertEquals(ea2.asString(), addr2);
	}

	/**
	 *
	 */
	public void testAsInternetAddress() throws AddressException {
		final InternetAddress addr0 = new InternetAddress("test@test.com");
		final InternetAddress addr1 = new InternetAddress("test@test.net");
		final InternetAddress addr2 = new InternetAddress("admin@test.net");

		final EmailAddress ea0 = new EmailAddress(addr0.toString());
		final EmailAddress ea1 = new EmailAddress(addr1.toString());
		final EmailAddress ea2 = new EmailAddress(addr2.toString());

		assertEquals(ea0.asInternetAddress(), addr0);
		assertEquals(ea1.asInternetAddress(), addr1);
		assertEquals(ea2.asInternetAddress(), addr2);
	}

	public void testIsValid() {
		final String addr0 = "test@test.com";
		final String addr1 = "test@test.net";
		final String addr2 = "admin@test.net";

		final EmailAddress ea0 = new EmailAddress(addr0);
		final EmailAddress ea1 = new EmailAddress(addr1);
		final EmailAddress ea2 = new EmailAddress(addr2);

		assertTrue(ea0.isValid());
		assertTrue(ea1.isValid());
		assertTrue(ea2.isValid());

		final EmailAddress invalidEa = EmailAddress.EMPTY;

		assertFalse(invalidEa.isValid());
	}
}

