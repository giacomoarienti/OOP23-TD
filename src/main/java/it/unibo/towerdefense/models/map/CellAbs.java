package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.engine.Position;
/**
 * An abstract class that implements methods of a generic Cell.
 */
public abstract class CellAbs implements Cell {

    private final int x;
    private final int y;

    /**
     * Constructor from coordinates and 2 opposite vertex positions.
     * @param coords coordinates x y to identify the cell in the map.
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
        return new LogicalPosition((int) ((x + 0.5) * LogicalPosition.SCALING_FACTOR),
            (int) ((y + 0.5) * LogicalPosition.SCALING_FACTOR));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final LogicalPosition position) {
        return position.getCellX() == x && position.getCellY() == y;
    }

    /**
     * Two Cells are equals if they are in the same poistion.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Cell && ((Cell) obj).getX() == this.getX() && ((Cell) obj).getY() == this.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (Integer.valueOf(this.getY())).hashCode() + (Integer.valueOf(this.getX())).hashCode();
    }


}
