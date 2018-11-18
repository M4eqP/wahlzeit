package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
    @Test
    public void testCreateLocationFromCoordinate() {
        Coordinate c = new CartesianCoordinate(2, 2, 2);
        Location l = new Location(c);
        assertTrue(l.getCoordinate().isEqual(c));
        assertTrue(l.getCoordinate().isEqual(new CartesianCoordinate(2, 2, 2)));
        assertFalse(l.getCoordinate().isEqual(new CartesianCoordinate(2, 4, 6)));
    }

    @Test
    public void testCreateLocationFromXYZValues() {
        Location l = new Location(1, 1, 1);
        assertTrue(l.getCoordinate().isEqual(new CartesianCoordinate(1, 1, 1)));
        assertFalse(l.getCoordinate().isEqual(new CartesianCoordinate(2, 4, 6)));
    }
}
