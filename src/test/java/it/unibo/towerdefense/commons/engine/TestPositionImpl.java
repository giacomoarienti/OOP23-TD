package it.unibo.towerdefense.commons.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the PositionImpl class.
 */
class TestPositionImpl {

    private static final int START_X = 2;
    private static final int START_Y = 3;
    private static final int ZERO = 0;

    private Position pos;
    private Position zero;

    /**
     * Create a Position object.
     */
    @BeforeEach
    void setUp() {
        this.pos = new PositionImpl(START_X, START_Y);
        this.zero = new PositionImpl(ZERO, ZERO);
    }

    /**
     * Test add method.
     */
    @Test
    void testAdd() {
        final Position pos2 = new PositionImpl(this.pos);
        this.pos.add(pos2);
        Assertions.assertEquals(START_X + START_X, pos.getX());
        Assertions.assertEquals(START_Y + START_Y, pos.getY());
    }

    /**
     * Test subtract method.
     */
    @Test
    void testSubtract() {
        final Position pos2 = new PositionImpl(this.pos);
        pos.subtract(pos2);
        Assertions.assertEquals(this.zero, this.pos);
    }

    /**
     * Test distanceTo method.
     */
    @Test
    void testDistanceTo() {
        // distance from itself
        final Position pos2 = new PositionImpl(this.pos);
        Assertions.assertEquals(0, this.pos.distanceTo(pos2));
        // distance from origin
        final double distance = Math.sqrt(START_X * START_X + START_Y * START_Y);
        Assertions.assertEquals(distance, this.pos.distanceTo(zero));
    }

    /**
     * Test toJson and fromJson method.
     */
    @Test
    void testJson() {
        final String json = this.pos.toJSON();
        final Position pos2 = PositionImpl.fromJson(json);
        Assertions.assertEquals(this.pos, pos2);
    }
}
