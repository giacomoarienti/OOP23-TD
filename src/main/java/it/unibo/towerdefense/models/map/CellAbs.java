package it.unibo.towerdefense.models.map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.models.engine.SizeImpl;

/**
 * An abstract class that implements methods of a generic Cell.
 */
public abstract class CellAbs implements Cell {

    private final int i;
    private final int j;
    private final Position topLeft;
    private final Position downRight;

    /**
     * Constructor from coordinates and 2 opposite vertex positions.
     * @param coords coordinates i j to identify the cell in the map.
     * @param topLeft position of top left vertex of the cell.
     * @param downRight position of down right vertex of the cell.
     */
    public CellAbs(final Coords coords, final Position topLeft, final Position downRight) {
        this.i = coords.x();
        this.j = coords.y();
        this.topLeft = new PositionImpl(topLeft);
        this.downRight = new PositionImpl(downRight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getI() {
        return i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getJ() {
        return j;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Position, Position> getOppositeVertex() {
        return Pair.of(topLeft, downRight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getSize() {
        return new SizeImpl((int) (downRight.getX() - topLeft.getX()), (int) (downRight.getY() - topLeft.getY()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Position position) {
        return position.getX() >= topLeft.getX() && position.getX() <= downRight.getX()
            && position.getY() >= topLeft.getY() && position.getY() <= downRight.getY();
    }

    /**
     * Two Cells are equals if they are in the same poistion.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Cell && ((Cell) obj).getI() == this.getI() && ((Cell) obj).getJ() == this.getJ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (Integer.valueOf(this.getJ())).hashCode() + (Integer.valueOf(this.getI())).hashCode();
    }

}
