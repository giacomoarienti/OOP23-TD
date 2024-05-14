package it.unibo.towerdefense.commons.engine;

import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.PositionImpl;
import it.unibo.towerdefense.commons.engine.Vector2D;
import it.unibo.towerdefense.commons.engine.Vector2DImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the Vector2DImpl class.
 */
class TestVector2DImpl {

    private static final int START_X = 2;
    private static final int START_Y = 3;
    private static final int MULTIPLY_SCALAR = 2;
    private static final int SCALAR_INVERT_DIR = -1;

    private Vector2D vec;

    /**
     * Create a Vector2D object.
     */
    @BeforeEach
    void setUp() {
        this.vec = new Vector2DImpl(START_X, START_Y);
    }

    /**
     * Test add and multiply methods.
     */
    @Test
    void testAddMultiply() {
        final Vector2D vec2 = new Vector2DImpl(vec);
        vec.add(vec2);
        Assertions.assertEquals(START_X + START_X, vec.getX());
        Assertions.assertEquals(START_Y + START_Y, vec.getY());
        // test multiply
        vec2.multiply(MULTIPLY_SCALAR);
        Assertions.assertEquals(vec2, vec);
    }

    /**
     * Test dot method.
     */
    @Test
    void testDot() {
        // the dot product should be the sum of the products of the components
        final int dotVec = vec.getX() * vec.getX() + vec.getY() * vec.getY();
        Assertions.assertEquals(dotVec, Vector2DImpl.dot(vec, vec));
    }

    /**
     * Test direction method.
     */
    @Test
    void testDirection() {
        final Position pos1 = new PositionImpl(1, 1);
        final Position pos2 = new PositionImpl(3, 4);
        Assertions.assertEquals(vec, Vector2DImpl.direction(pos1, pos2));
        // test with opposite direction
        vec.multiply(SCALAR_INVERT_DIR);
        Assertions.assertEquals(vec, Vector2DImpl.direction(pos2, pos1));
    }
}
