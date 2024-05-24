package it.unibo.towerdefense.model.map;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.PositionImpl;

/**
 * 4 cardinal directions as 2D veresr.
 */
public enum MapDirection {

    /** East. */
    E(+1, 0, Direction.E),
    /** North. */
    N(0, -1, Direction.N),
    /** West. */
    W(-1, 0, Direction.W),
    /** Sud. */
    S(0, +1, Direction.S);

    private final int horizontal;
    private final int vertical;
    private final Direction dir;

    /**
     * Constructor from verser component.
     * @param horizontal horizontal verser component.
     * @param vertical vertical verser component.
     * @param dir equivalent direction in commons.engine.Direction
     */
    MapDirection(final int horizontal, final int vertical, final Direction dir) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.dir = dir;
    }

    /**
     * Horizontal verser component getter.
     * @return values -1, 0 or +1
     */
    public int horizontal() {
        return horizontal;
    }

    /**
     * Vertical verser component getter.
     * @return values -1, 0 or +1
     */
    public int vertical() {
        return vertical;
    }

    /**
     * Verser getter.
     * @return Return verser as a Position
     */
    public Position asPosition() {
        return new PositionImpl(horizontal, vertical);
    }

    /**
     * Returns the equivalent direction in Commons.Engine.Direction .
     * @return the Direction.
     */
    public Direction asDirection() {
        return dir;
    }

}
