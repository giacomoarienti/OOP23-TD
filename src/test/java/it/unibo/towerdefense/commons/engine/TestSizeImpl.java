package it.unibo.towerdefense.commons.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Test class for the SizeImpl class.
 */
class TestSizeImpl {

    private static final int WIDTH = 2;
    private static final int HEIGHT = 3;

    private Size size = new SizeImpl(WIDTH, HEIGHT);

    /**
     * Test getWidth method.
     */
    @Test
    void testGetWidth() {
        Assertions.assertEquals(WIDTH, size.getWidth());
    }

    /**
     * Test getHeight method.
     */
    @Test
    void testGetHeight() {
        Assertions.assertEquals(HEIGHT, size.getHeight());
    }
}
