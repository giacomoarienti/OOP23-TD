package it.unibo.towerdefense.models.engine;

/**
 * Interface representing the position of an object
 * in a 2D space.
 */
public interface Position {

    /**
     * X getter.
     * @return the value of x
     */
    double getX();

    /**
     * Y getter.
     * @return the value of y
     */
    double getY();

    /**
     * X getter.
     * @return the value of x by rounding it
     */
    int intX();

    /**
     * Y getter.
     * @return the value of y by rounding it
     */
    int intY();

    /**
     * Updates the value of x.
     * @param x new x value
     */
    void setX(double x);

    /**
     * Updates the value of y.
     * @param y new y value
     */
    void setY(double y);

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
}
