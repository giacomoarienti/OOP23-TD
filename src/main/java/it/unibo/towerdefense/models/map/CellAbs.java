package it.unibo.towerdefense.models.map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.models.engine.Position;

/**
 * An abstract class that implements methods of a generic Cell.
 */
public abstract class CellAbs implements Cell {

    private final int i;
    private final int j;

    /**
     * Construct from i, j indexes.
     * @param i orizontal index
     * @param j vertical index
     */
    public CellAbs(final int i, final int j) {
        this.i = i;
        this.j = j;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOppositeVertex'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
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
        return ((Integer) this.getJ()).hashCode() + ((Integer) this.getI()).hashCode();
    }

}
