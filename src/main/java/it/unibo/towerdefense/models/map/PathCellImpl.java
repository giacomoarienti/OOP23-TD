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
     * @param topLeft position of top left vertex of the cell.
     * @param downRight position of down right vertex of the cell.
     * @param distanceToEnd number of cell between this and end of path.
     */
    public PathCellImpl(final Coords coords, final Position topLeft, final Position downRight, final int distanceToEnd) {
        super(coords, topLeft, downRight);
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
