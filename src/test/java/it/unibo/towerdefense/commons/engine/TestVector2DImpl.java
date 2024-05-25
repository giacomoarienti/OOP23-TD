package it.unibo.towerdefense.commons.engine;

import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the Vector2DImpl class.
 */
@SuppressFBWarnings(
    value = "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
    justification = "Field is initialized in @BeforeEach method."
)
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
        Assertions.assertNotNull(this.vec);
    }

    /**
     * Test add and multiply methods.
     */
    @Test
    void testAddMultiply() {
        // create a copy of the vector
        final Vector2D vec2 = new Vector2DImpl(this.vec);
        this.vec.add(vec2);
        Assertions.assertEquals(START_X + START_X, this.vec.getX());
        Assertions.assertEquals(START_Y + START_Y, this.vec.getY());
        // test multiply
        vec2.multiply(MULTIPLY_SCALAR);
        Assertions.assertEquals(vec2, this.vec);
    }

    /**
     * Test dot method.
     */
    @Test
    void testDot() {
        // the dot product should be the sum of the products of the components
        final int dotVec = this.vec.getX() * this.vec.getX() + this.vec.getY() * this.vec.getY();
        Assertions.assertEquals(dotVec, Vector2DImpl.dot(this.vec, this.vec));
    }

    /**
     * Test direction method.
     */
    @Test
    void testDirection() {
        // create two positions
        final Position pos1 = new PositionImpl(1, 1);
        final Position pos2 = new PositionImpl(3, 4);
        Assertions.assertEquals(this.vec, Vector2DImpl.direction(pos1, pos2));
        // test with opposite direction
        this.vec.multiply(SCALAR_INVERT_DIR);
        Assertions.assertEquals(this.vec, Vector2DImpl.direction(pos2, pos1));
    }
}
