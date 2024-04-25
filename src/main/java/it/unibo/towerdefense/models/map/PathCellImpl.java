package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.models.engine.Position;

/**
 * Class that implements PathCell methods.
 */
public class PathCellImpl extends CellAbs implements PathCell {

    //private final int distanceToEnd;
    private final Direction in;
    private final Direction out;

    /**
     *Constructor from coordinates, 2 opposite vertex positions and distance to end of path.
     * @param coords coordinates i j to identify the cell in the map.
     * @param in direction to enter the cell.
     * @param out direction to exit the cell.
     */
    public PathCellImpl(final Position coords, final Direction in, final Direction out) {
        super(coords);
        //this.distanceToEnd = distanceToEnd;
        this.in = in;
        this.out = out;
    }

    /**
     * {@inheritDoc}
     */
    /*@Override
    public int distanceToEnd() {
        return distanceToEnd;
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getInDirection() {
        return in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getOutDirection() {
        return out;
    }

}
