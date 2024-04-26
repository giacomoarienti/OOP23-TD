package it.unibo.towerdefense.models.engine;

import it.unibo.towerdefense.models.Copyable;
import it.unibo.towerdefense.models.JsonSerializable;

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
     * Return the position of origin (0,0).
     * @return the position of origin
     */
    static Position origin() {
        return new PositionImpl(0, 0);
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
