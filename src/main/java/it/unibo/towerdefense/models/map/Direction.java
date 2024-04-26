package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;

/**
 * 4 cardinal directions as 2D verosr.
 */
public enum Direction {

    /** North. */
    N(0, -1),
    /** West. */
    W(-1, 0),
    /** Sud. */
    S(0, +1),
    /** East. */
    E(+1, 0);

    private final int orizontal;
    private final int vertical;

    /**
     * Constructor from versor component.
     * @param orizontal
     * @param vertical
     */
    Direction(final int orizontal, final int vertical) {
        this.orizontal = orizontal;
        this.vertical = vertical;
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

}
