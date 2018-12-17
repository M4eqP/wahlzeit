package org.wahlzeit.model;

import java.util.*;

/**
 * Package-local service class implementing the value object semantics for Coordinate classes. Thread-safe.
 */
class CoordinateService {
    // instances caches
    private static final ArrayList<Coordinate> cartesianCoordinateCache = new ArrayList<>();
    private static final ArrayList<Coordinate> sphericCoordinateCache = new ArrayList<>();

    private static Coordinate lookUpCache(final ArrayList<Coordinate> cache, Coordinate temp) {
        // efficient lookups aren't part of the requirements
        for (Coordinate existingCoord : cache) {
            // compare values only, hash would be pointless as temp is definitely a new object with a different hash
            if (existingCoord.isEqual(temp))
                return existingCoord;
        }

        return null;
    }

    /**
     * @methodtype factory
     */
    public static CartesianCoordinate getCartesianCoordinate(final int x, final int y, final int z) {
        // create a new, temporary instance that can be used to look up the cache
        CartesianCoordinate temp = new CartesianCoordinate(x, y, z);

        // ensure thread-safety of this helper
        synchronized (cartesianCoordinateCache) {
            CartesianCoordinate existing = (CartesianCoordinate) lookUpCache(cartesianCoordinateCache, temp);

            // if we find an existing object, we must return it
            if (existing != null)
                return existing;

            // otherwise, we just add our new object to the cache, and return that one instead
            cartesianCoordinateCache.add(temp);
            return temp;
        }
    }

    /**
     * @methodtype factory
     */
    public static SphericCoordinate getSphericCoordinate(final int phi, final int theta, final int radius) {
        // create a new, temporary instance that can be used to look up the cache
        SphericCoordinate temp = new SphericCoordinate(phi, theta, radius);

        // ensure thread-safety of this helper
        synchronized (sphericCoordinateCache) {
            SphericCoordinate existing = (SphericCoordinate) lookUpCache(sphericCoordinateCache, temp);

            // if we find an existing object, we must return it
            if (existing != null)
                return existing;

            // otherwise, we just add our new object to the cache, and return that one instead
            sphericCoordinateCache.add(temp);
            return temp;
        }
    }
}
