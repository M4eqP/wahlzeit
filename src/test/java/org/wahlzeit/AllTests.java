/*
 * Copyright (c) 2018 by M4eqP@users.noreply.github.com
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

package org.wahlzeit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.handlers.*;
import org.wahlzeit.model.*;
import org.wahlzeit.model.persistence.*;
import org.wahlzeit.services.*;
import org.wahlzeit.services.mailing.*;
import org.wahlzeit.utils.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    // handlers tests
    TellFriendTest.class,

    // model tests
    DatastoreAdapterTest.class,
    AccessRightsTest.class,
    CoordinateTest.class,
    FlagReasonTest.class,
    GenderTest.class,
    GuestTest.class,
    LocationTest.class,
    PhotoFilterTest.class,
    TagsTest.class,
    UserStatusTest.class,
    ValueTest.class,
    ChestnutPhotoFactoryTest.class,
    ChestnutPhotoManagerTest.class,
    ChestnutCompositionTest.class,
    ChestnutPhotoTest.class,
    SingletonManagerTest.class,

    // utils tests
    StringUtilTest.class,
    VersionTest.class,

    // services tests
    // new test suite
    EmailServiceTestSuite.class,
    // existing test case
    EmailAddressTest.class,
})
public class AllTests {}
