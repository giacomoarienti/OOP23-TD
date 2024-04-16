package it.unibo.towerdefense.models.engine;

import com.google.common.base.Objects;

/**
 * Class implementing the concept of Vector
 * in a 2D space.
 */
public class Vector2DImpl implements Vector2D {

    private int x;
    private int y;

    /**
     * Constructor from x,y coordinates.
     * @param x first coordinate
     * @param y second coordinate
     */
    public Vector2DImpl(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor from given vector.
     * @param vec the vector to copy from.
     */
    public Vector2DImpl(final Vector2D vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void multiply(final int scalar) {
        x *= scalar;
        y *= scalar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final Vector2D vector) {
        x += vector.getX();
        y += vector.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Vector2DImpl) {
            final Vector2DImpl vec = (Vector2DImpl) o;
            return this.getX() == vec.getX()
                && this.getY() == vec.getY();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getX(), this.getY());
    }

    /**
     * Return the Vector representing the direction from start to end.
     * @param start the starting position
     * @param end the final position
     * @return the direction's Vector
     */
    public static Vector2D direction(final Position start, final Position end) {
        return new Vector2DImpl(end.getX() - start.getX(), end.getY() - start.getY());
    }

    /**
     * Calculates the dot product of the 2 vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return the result of the dot product
     */
    public static int dot(final Vector2D v1, final Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
}
