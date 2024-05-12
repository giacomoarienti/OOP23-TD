package it.unibo.towerdefense.models.map;

import org.json.JSONObject;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;

/**
 * Class that implements PathCell methods.
 */
public class PathCellImpl extends CellAbs implements PathCell {

    private final Direction in;
    private final Direction out;
    private final int distanceToEnd;

    /**
     *Constructor from coordinates, 2 opposite vertex positions and distance to end of path.
     * @param coords coordinates i j to identify the cell in the map.
     * @param in direction to enter the cell.
     * @param out direction to exit the cell.
     */
    public PathCellImpl(final Position coords, final Direction in, final Direction out, int distanceToEnd) {
        super(coords);
        this.in = in;
        this.out = out;
        this.distanceToEnd = distanceToEnd;
    }

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

    @Override
    public int distanceToEnd() {
        return distanceToEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition inSideMidpoint() {
        return new LogicalPosition(getCenter().getX() - getInDirection().vertical() * LogicalPosition.SCALING_FACTOR / 2,
            getCenter().getY() - getInDirection().orizontal() * LogicalPosition.SCALING_FACTOR / 2);
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
            jsonObject.getEnum(Direction.class, "in"),
            jsonObject.getEnum(Direction.class, "out"),
            jsonObject.getInt("distance")
        );
    }
}
