package it.unibo.towerdefense.commons.engine;

import it.unibo.towerdefense.commons.api.Copyable;
import it.unibo.towerdefense.commons.api.JsonSerializable;

/**
 * Interface representing the position of an object
 * in a 2D space.
 */
public interface Position extends JsonSerializable, Copyable<Position> {

    /**
     * X getter.
     * @return the value of x
     */
    int getX();

    /**
     * Y getter.
     * @return the value of y
     */
    int getY();

    /**
     * Updates the value of x.
     * @param x new x value
     */
    void setX(int x);

    /**
     * Updates the value of y.
     * @param y new y value
     */
    void setY(int y);

    /**
     * Add position's components (x,y) to the current position.
     * @param position where the components are got from
     */
    void add(Position position);

    /**
     * Add position's components (x,y) to the current position.
     * @param position where the components are got from
     */
    void subtract(Position position);

    /**
     * Calculates the distance from the given position.
     * @param position to calculate the distance from
     * @return the distance between the two positions
     */
    double distanceTo(Position position);

    /**
     * Factory method to create a new Position object.
     * @param x the x value of the position
     * @param y the y value of the position
     * @return the new Position object
     */
    static Position of(final int x, final int y) {
        return new PositionImpl(x, y);
    }

    /**
     * Factory method to create a new Position object.
     * The position is initialized with the origin (0,0).
     * @return the new Position object
     */
    static Position origin() {
        return Position.of(0, 0);
    }

    /**
     * Returns the Position object from JSON string.
     * @param jsonData the JSON representation
     * @return the Position object
     */
    static Position fromJson(final String jsonData) {
        return PositionImpl.fromJson(jsonData);
    }
}
