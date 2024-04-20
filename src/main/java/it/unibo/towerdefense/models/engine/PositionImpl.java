package it.unibo.towerdefense.models.engine;

import com.google.common.base.Objects;

/**
 * Class that models the concept of Position in a 2D space.
 */
public class PositionImpl implements Position {

    private int x;
    private int y;

    /**
     * Construct from x,y coords.
     * @param x coord
     * @param y coord
     */
    public PositionImpl(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct from given position's coords.
     * @param position to copy from
     */
    public PositionImpl(final Position position) {
        this.x = position.getX();
        this.y = position.getY();
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
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final Position position) {
        x += position.getX();
        y += position.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subtract(final Position position) {
        x -= position.getX();
        y -= position.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distanceTo(final Position other) {
        final int deltaX = this.getX() - other.getX();
        final int deltaY = this.getY() - other.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof PositionImpl) {
            final PositionImpl positionObject = (PositionImpl) o;
            return this.getX() == positionObject.getX()
                && this.getY() == positionObject.getY();
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
}