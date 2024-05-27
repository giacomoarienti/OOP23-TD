package it.unibo.towerdefense.model.map;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Data transfer Object that describes a position of a enemy on the path.
 */
public class PathVector {

    private final LogicalPosition position;
    private final Direction direction;
    private final int distanceToEnd;

    /**
     * Constructor from position, direction and distance to end.
     * @param position position
     * @param direction direction in this position
     * @param distanceToEnd distance from end of path
     */
    public PathVector(final LogicalPosition position, final Direction direction, final int distanceToEnd) {
        this.position = LogicalPosition.copyOf(position);
        this.direction = direction;
        this.distanceToEnd = distanceToEnd;
    }

    /**
     * Position getter.
     * @return Logical position of enemy.
     */
    public LogicalPosition position() {
        return LogicalPosition.copyOf(position);
    }

    /**
     * Direction getter.
     * @return Direction of enemy movement.
     */
    public Direction direction() {
        return direction;
    }

    /**
     * Distance to end getter.
     * @return distance from end of path, if equals to 0 means that enemy is arrived.
     */
    public int distanceToEnd() {
        return distanceToEnd;
    }
};
