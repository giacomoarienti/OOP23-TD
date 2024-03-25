package it.unibo.towerdefense.models.map;

/**
 * Class that implements PathCell methods.
 */
public class PathCellImpl extends CellAbs implements PathCell {

    private final int distanceToEnd;

    /**
     *Constructor from position and distance to end of path.
     * @param i the orizontal index of the cell in the map
     * @param j the vertical index of the cell in the map
     * @param distanceToEnd number of cell between this and end of path.
     */
    public PathCellImpl(final int i, final int j, final int distanceToEnd) {
        super(i, j);
        this.distanceToEnd = distanceToEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int distanceToEnd() {
        return distanceToEnd;
    }

}
