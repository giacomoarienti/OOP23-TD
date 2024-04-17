package it.unibo.towerdefense.models.enemies;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.models.map.PathCell;

/**
 * Utility class for memorizing and converting the enemies' position.
 */
public class EnemyPosition implements Cloneable {

    static final int SCALING_FACTOR = 3600; // 1/scalingFactor of a cell is the smallest variation registered
    // a cell is scalingFactor positions long

    private int x;
    private int y;

    /**
     * Constructor for the class.
     *
     * @param x x
     * @param y y
     */
    public EnemyPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Static factory method for constructing from a PathCell instance.
     *
     * @param c the PathCell to be converted
     * @return the created EnemyPosition
     */
    static EnemyPosition fromPathCell(final PathCell c) {
        throw new UnsupportedOperationException();
    }

    

    /**
     * Getter for the x.
     *
     * @return the x
     */
    int getX() {
        return x;
    }

    /**
     * Getter for the y.
     *
     * @return the y
     */
    int getY() {
        return y;
    }

    /**
     * Used when calculating distance between two adjacent cells.
     *
     * @param pos the other position
     * @return the distance
     */
    int distanceFromAdjacent(final EnemyPosition pos) {
        return pos.getX() == x ? pos.getY() - y : pos.getX() - x;
    }

    /**
     * Setter for the position.
     *
     * @param x new x
     * @param y new y
     */
    void set(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Converts to PathCell.
     *
     * @return the constructed PathCell
     */
    PathCell toPathCell() {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts to Pair of Integers.
     *
     * @return the position as Pair of Integers.
     */
    public Pair<Integer, Integer> asPair() {
        return Pair.of(x, y);
    };

    
}
