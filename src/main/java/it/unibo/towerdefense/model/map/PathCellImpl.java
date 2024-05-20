package it.unibo.towerdefense.model.map;

import org.json.JSONObject;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;

/**
 * Class that implements PathCell methods.
 */
public class PathCellImpl extends CellAbs implements PathCell {

    private final MapDirection in;
    private final MapDirection out;
    private final int distanceToEnd;

    /**
     *Constructor from coordinates, 2 opposite vertex positions and distance to end of path.
     * @param coords coordinates i j to identify the cell in the map.
     * @param in direction to enter the cell.
     * @param out direction to exit the cell.
     * @param distanceToEnd numbers of cell from this to end of path.
     */
    public PathCellImpl(final Position coords, final MapDirection in, final MapDirection out, final int distanceToEnd) {
        super(coords);
        this.in = in;
        this.out = out;
        this.distanceToEnd = distanceToEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapDirection getInDirection() {
        return in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapDirection getOutDirection() {
        return out;
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
    public LogicalPosition inSideMidpoint() {
        return new LogicalPosition(getCenter().getX() - getInDirection().orizontal() * LogicalPosition.SCALING_FACTOR / 2 + getInDirection().orizontal(),
            getCenter().getY() - getInDirection().vertical() * LogicalPosition.SCALING_FACTOR / 2 + getInDirection().vertical());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put("pos", ((Position) this).toJSON())
            .put("in", this.in)
            .put("out", this.out)
            .put("distance", distanceToEnd)
            .toString();
    }

    /**
     * Returns the PathCell object from JSON string.
     * @param jsonData the JSON representation
     * @return the PathCell object
     */
    public static PathCell fromJson(final String jsonData) {
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new PathCellImpl(
            Position.fromJson(jsonObject.getString("pos")),
            jsonObject.getEnum(MapDirection.class, "in"),
            jsonObject.getEnum(MapDirection.class, "out"),
            jsonObject.getInt("distance")
        );
    }
}
