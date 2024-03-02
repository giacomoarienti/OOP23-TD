package it.unibo.towerdefense.models.map;

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

}
