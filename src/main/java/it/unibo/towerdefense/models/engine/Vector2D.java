package it.unibo.towerdefense.models.engine;

/**
 * Interface representing a vector in a 2D space.
 */
public interface Vector2D {

    /**
     * X getter.
     * @return the value of x.
     */
    double getX();

    /**
     * Y getter.
     * @return the value of y.
     */
    double getY();

    /**
     * Calculate the length of the vector.
     * @return the length of the vector.
     */
    double length();

    /**
     * Multiply the vector components by a scalar.
     * @param scalar the scalar to multiply by.
     */
    void multiply(double scalar);

    /**
     * Adds the component of a vector to
     * the current one.
     * @param vector the Vector to add
     */
    void add(Vector2D vector);

}
