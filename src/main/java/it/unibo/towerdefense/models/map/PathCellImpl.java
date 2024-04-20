package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.models.engine.Position;

/**
 * Class that implements PathCell methods.
 */
public class PathCellImpl extends CellAbs implements PathCell {

    private final int distanceToEnd;

    /**
     *Constructor from coordinates, 2 opposite vertex positions and distance to end of path.
     * @param coords coordinates i j to identify the cell in the map.
     * @param distanceToEnd number of cell between this and end of path.
     */
    public PathCellImpl(final Position coords, final int distanceToEnd) {
        super(coords);
        this.distanceToEnd = distanceToEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int distanceToEnd() {
        return distanceToEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getInDirection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInDirection'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getOutDirection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOuDirection'");
    }

}
