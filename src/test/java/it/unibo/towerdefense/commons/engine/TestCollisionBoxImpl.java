package it.unibo.towerdefense.commons.engine;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the CollisionBoxImpl class.
 */
class TestCollisionBoxImpl {

    private static final int START_X = 2;
    private static final int START_Y = 3;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 6;
    private static final int ZERO = 0;


    /**
     * Test collidesWith method.
     */
    @Test
    void testCollidesWith() {
        final Size size = new SizeImpl(WIDTH, HEIGHT);
        // test colliding boxes
        final CollisionBox col1 = new CollisionBoxImpl(
            new PositionImpl(START_X, START_Y),
            size
        );
        final CollisionBox col2 = new CollisionBoxImpl(
            new PositionImpl(ZERO, ZERO),
            size);
        Assertions.assertTrue(col1.collidesWith(col2));
        // test not colliding boxes
        final CollisionBox col3 = new CollisionBoxImpl(
            new PositionImpl(START_X + WIDTH, START_Y + HEIGHT),
            size
        );
        Assertions.assertFalse(col1.collidesWith(col3));
    }
}
