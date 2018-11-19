package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CartesianCoordinateTest {
    @Test
    public void testEquals() {
        CartesianCoordinate c1a = new CartesianCoordinate(0, 0, 0);
        CartesianCoordinate c1b = new CartesianCoordinate(0, 0, 0);

        assertTrue(c1a.equals(c1a));
        assertTrue(c1b.equals(c1b));

        assertTrue(c1a.equals(c1b));
        assertTrue(c1b.equals(c1a));
        
        CartesianCoordinate c2 = new CartesianCoordinate(1, 1, 1);
        assertFalse(c1a.equals(c2));
        assertFalse(c2.equals(c1b));
    }
    
    @Test
    public void testIsEqual() {
        CartesianCoordinate c1a = new CartesianCoordinate(0, 0, 0);
        CartesianCoordinate c1b = new CartesianCoordinate(0, 0, 0);

        assertTrue(c1a.isEqual(c1a));
        assertTrue(c1b.isEqual(c1b));

        assertTrue(c1a.isEqual(c1b));
        assertTrue(c1b.isEqual(c1a));

        CartesianCoordinate c2 = new CartesianCoordinate(1, 1, 1);
        assertFalse(c1a.isEqual(c2));
        assertFalse(c2.isEqual(c1b));
    }

    @Test
    public void testGetCartesianDistance() {
        CartesianCoordinate c1 = new CartesianCoordinate(0, 0, 0);
        CartesianCoordinate c2 = new CartesianCoordinate(1, 1, 1);
        CartesianCoordinate c3 = new CartesianCoordinate(1, 2, 3);

        assertEquals(0.0, c1.getCartesianDistance(c1), 0.000001);
        assertEquals(Math.sqrt(3), c1.getCartesianDistance(c2), 0.000001);
        assertEquals(Math.sqrt(14), c1.getCartesianDistance(c3), 0.000001);

        assertEquals(Math.sqrt(5), c2.getCartesianDistance(c3), 0.000001);
    }

    @Test
    public void testGetters() {
        CartesianCoordinate c1 = new CartesianCoordinate(10, 20, 30);
        assertEquals(10, c1.getX(), 0.000001);
        assertEquals(20, c1.getY(), 0.000001);
        assertEquals(30, c1.getZ(), 0.000001);
    }

    @Test
    public void testConversionFromAndToSphericCoordinate() {
        CartesianCoordinate c1 = new CartesianCoordinate(10, 20 ,30);
        CartesianCoordinate c2 = c1.asSphericCoordinate().asCartesianCoordinate();

        assertEquals(c1.getX(), c2.getX(), 0.000001);
        assertEquals(c1.getY(), c2.getY(), 0.000001);
        assertEquals(c1.getZ(), c2.getZ(), 0.000001);
    }
}
