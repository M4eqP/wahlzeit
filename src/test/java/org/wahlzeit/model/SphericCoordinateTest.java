package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SphericCoordinateTest {
    @Test
    public void testEquals() {
        SphericCoordinate c1a = new SphericCoordinate(0, 0, 0);
        SphericCoordinate c1b = new SphericCoordinate(0, 0, 0);

        assertTrue(c1a.equals(c1a));
        assertTrue(c1b.equals(c1b));

        assertTrue(c1a.equals(c1b));
        assertTrue(c1b.equals(c1a));
        
        SphericCoordinate c2 = new SphericCoordinate(1, 1, 1);
        assertFalse(c1a.equals(c2));
        assertFalse(c2.equals(c1b));
    }
    
    @Test
    public void testIsEqual() {
        SphericCoordinate c1a = new SphericCoordinate(0, 0, 0);
        SphericCoordinate c1b = new SphericCoordinate(0, 0, 0);

        assertTrue(c1a.isEqual(c1a));
        assertTrue(c1b.isEqual(c1b));

        assertTrue(c1a.isEqual(c1b));
        assertTrue(c1b.isEqual(c1a));

        SphericCoordinate c2 = new SphericCoordinate(1, 1, 1);
        assertFalse(c1a.isEqual(c2));
        assertFalse(c2.isEqual(c1b));
    }

    @Test
    public void testGetSphericDistance() {
        SphericCoordinate c1 = new SphericCoordinate(0, 0, 0);
        SphericCoordinate c2 = new SphericCoordinate(1, 1, 1);
        SphericCoordinate c3 = new SphericCoordinate(1, 2, 3);

        assertEquals(0.0, c1.getCentralAngle(c1), 0.000001);

        assertEquals(0.8692655149272476, c1.getCentralAngle(c2), 0.000001);
        assertEquals(1.7826756004986242, c1.getCentralAngle(c3), 0.000001);

        assertEquals(1.0, c2.getCentralAngle(c3), 0.000001);
    }

    @Test
    public void testGetters() {
        SphericCoordinate c1 = new SphericCoordinate(2, 3, 30);
        assertEquals(2, c1.getPhi(), 0.000001);
        assertEquals(3, c1.getTheta(), 0.000001);
        assertEquals(30, c1.getRadius(), 0.000001);
    }

    @Test
    public void testConversionFromAndToCartesianCoordinate() {
        SphericCoordinate c1 = new SphericCoordinate(-3, 2 ,30);
        SphericCoordinate c2 = c1.asCartesianCoordinate().asSphericCoordinate();

        assertEquals(c1.getPhi(), c2.getPhi(), 0.001);
        assertEquals(c1.getTheta(), c2.getTheta(), 0.001);
        assertEquals(c1.getRadius(), c2.getRadius(), 0.001);
    }

    private void assertConstructorThrowsInvalidArgumentException(double phi, double theta, double radius) {
        try {
            new SphericCoordinate(phi, theta, radius);
        } catch (IllegalArgumentException e) {
            return;
        }

        String values = phi + " " + theta + " " + radius;
        fail("Constructor did not throw IllegalArgumentException for values " + values);
    }

    @Test
    public void testConstructFromValuesOutsideRange() {
        // check borders of ranges
        new SphericCoordinate(-Math.PI, 0, 30);
        new SphericCoordinate(Math.PI - 0.0001, Math.PI, 30);
        new SphericCoordinate(Math.PI - 0.0001, Math.PI, 0);

        // invalid phi values
        assertConstructorThrowsInvalidArgumentException(-Math.PI - 0.0001, 0, 30);
        assertConstructorThrowsInvalidArgumentException(Math.PI, 0, 30);

        // invalid theta values
        assertConstructorThrowsInvalidArgumentException(1, -0.0001, 30);
        assertConstructorThrowsInvalidArgumentException(1, Math.PI + 0.0001, 30);

        // invalid radius values
        assertConstructorThrowsInvalidArgumentException(1, 1, -50);
    }
}
