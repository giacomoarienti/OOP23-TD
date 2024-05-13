package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.PositionImpl;

/**
 * 4 cardinal directions as 2D verosr.
 */
public enum MapDirection {

    /** East. */
    E(+1, 0, Direction.E),
    /** North. */
    N(0, +1, Direction.N),
    /** West. */
    W(-1, 0, Direction.W),
    /** Sud. */
    S(0, -1, Direction.S);

    private final int orizontal;
    private final int vertical;
    private final Direction dir;

    /**
     * Constructor from versor component.
     * @param orizontal
     * @param vertical
     * @dir equivalent direction in commons.engine.Direction
     */
    MapDirection(final int orizontal, final int vertical, final Direction dir) {
        this.orizontal = orizontal;
        this.vertical = vertical;
        this.dir = dir;
    }

    /**
     * Orizontal versor component getter.
     * @return values -1, 0 or +1
     */
    public int orizontal() {
        return orizontal;
    }

    /**
     * Vertical versor component getter.
     * @return values -1, 0 or +1
     */
    public int vertical() {
        return vertical;
    }

    /**
     * Versor getter.
     * @return Return versor as a Position
     */
    public Position asPosition() {
        return new PositionImpl(orizontal, vertical);
    }

    /**
     * Returns the equivalent direction in Commons.Engine.Direction .
     * @return the Direction.
     */
    public Direction asDirection() {
        return dir;
    }

}
