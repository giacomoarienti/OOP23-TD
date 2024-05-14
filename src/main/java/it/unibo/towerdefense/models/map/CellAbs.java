package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.PositionImpl;
/**
 * An abstract class that implements methods of a generic Cell.
 */
public abstract class CellAbs extends PositionImpl implements Cell {

    /**
     * Constructor from coordinates.
     * @param coords coordinates to identify the cell in the map.
     */
    public CellAbs(final Position coords) {
        super(coords);
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
