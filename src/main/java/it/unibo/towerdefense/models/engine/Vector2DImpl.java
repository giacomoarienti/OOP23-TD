package it.unibo.towerdefense.models.engine;

/**
 * Class implementing the concept of Vector
 * in a 2D space.
 */
public class Vector2DImpl implements Vector2D {

    private double x;
    private double y;

    /**
     * Constructor from x,y coordinates.
     * @param x first coordinate
     * @param y second coordinate
     */
    public Vector2DImpl(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Normalizes the current vector.
     */
    private void normalize() {
        final double length = length();
        x = x == 0 ? 0 : x / length;
        y = y == 0 ? 0 : y / length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
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
    public void multiply(final double scalar) {
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
     * Creates a new Vector with the components of the given one.
     * @param vector the vector to copy from
     * @return a copy of the given vector
     */
    public static Vector2D copyOf(final Vector2D vector) {
        return new Vector2DImpl(vector.getX(), vector.getY());
    }

    /**
     * Return the Vector representing the direction from start to end.
     * @param start the starting position
     * @param end the final position
     * @return the direction's Vector
     */
    public static Vector2D direction(final Position start, final Position end) {
        final Vector2DImpl direction = new Vector2DImpl(start.getX() - end.getX(), start.getY() - end.getY());
        direction.normalize();
        return direction;
    }

    /**
     * Calculates the dot product of the 2 vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return the result of the dot product
     */
    public static double dot(final Vector2D v1, final Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
}
