package it.unibo.towerdefense.model.map;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;

/**
 * An abstract class that implements methods of a generic Cell.
 */
public abstract class CellAbs implements Cell {

    private final int x;
    private final int y;


    /**
     * Constructor from coordinates.
     * @param coords coordinates to identify the cell in the map.
     */
    public CellAbs(final Position coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition getCenter() {
        return new LogicalPosition((int) ((getX() + 0.5) * LogicalPosition.SCALING_FACTOR),
            (int) ((getY() + 0.5) * LogicalPosition.SCALING_FACTOR));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final LogicalPosition position) {
        return position.getCellX() == getX() && position.getCellY() == getY();
    }

}
