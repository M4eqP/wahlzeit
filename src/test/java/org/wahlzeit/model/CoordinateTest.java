package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {
    @Test
    public void testEquals() {
        Coordinate c1a = new Coordinate(0, 0, 0);
        Coordinate c1b = new Coordinate(0, 0, 0);

        assertTrue(c1a.equals(c1a));
        assertTrue(c1b.equals(c1b));

        assertTrue(c1a.equals(c1b));
        assertTrue(c1b.equals(c1a));
        
        Coordinate c2 = new Coordinate(1, 1, 1);
        assertFalse(c1a.equals(c2));
        assertFalse(c2.equals(c1b));
    }
    
    @Test
    public void testIsEqual() {
        Coordinate c1a = new Coordinate(0, 0, 0);
        Coordinate c1b = new Coordinate(0, 0, 0);

        assertTrue(c1a.isEqual(c1a));
        assertTrue(c1b.isEqual(c1b));

        assertTrue(c1a.isEqual(c1b));
        assertTrue(c1b.isEqual(c1a));

        Coordinate c2 = new Coordinate(1, 1, 1);
        assertFalse(c1a.isEqual(c2));
        assertFalse(c2.isEqual(c1b));
    }

    @Test
    public void testGetDistance() {
        Coordinate c1 = new Coordinate(0, 0, 0);
        Coordinate c2 = new Coordinate(1, 1, 1);
        Coordinate c3 = new Coordinate(1, 2, 3);

        assertEquals(0.0, c1.getDistance(c1), 0.000001);
        assertEquals(Math.sqrt(3), c1.getDistance(c2), 0.000001);
        assertEquals(Math.sqrt(14), c1.getDistance(c3), 0.000001);

        assertEquals(Math.sqrt(5), c2.getDistance(c3), 0.000001);
    }

    @Test
    public void testGetters() {
        Coordinate c1 = new Coordinate(10, 20, 30);
        assertEquals(10, c1.getX(), 0.000001);
        assertEquals(20, c1.getY(), 0.000001);
        assertEquals(30, c1.getZ(), 0.000001);
    }
}
