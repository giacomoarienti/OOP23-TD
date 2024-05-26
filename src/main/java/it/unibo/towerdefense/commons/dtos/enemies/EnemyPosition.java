package it.unibo.towerdefense.commons.dtos.enemies;

import java.util.Objects;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Class which extends LogicalPosition to add information related to the spatial
 * positioning of an enemy which are to be shared between different parts of the
 * application.
 */
public class EnemyPosition extends LogicalPosition {

    private Direction dir;
    private long distance;

    /**
     * The constructor for the class.
     *
     * @param x        the x
     * @param y        the y
     * @param dir      the direction the enemy is facing
     * @param distance the distance the enemy has walked
     */
    public EnemyPosition(final int x, final int y, final Direction dir, final long distance) {
        super(x, y);
        this.dir = dir;
        this.distance = distance;
    }

    /**
     * Sets the EnemyPosition to the new position.
     *
     * @param pos the position to copy
     */
    public void setTo(final EnemyPosition pos) {
        this.set(pos.getX(), pos.getY());
        this.setDir(pos.getDir());
        this.setDistance(pos.getDistanceWalked());
    }

    /**
     * Getter for the currently facing direction.
     *
     * @return the direction the Enemy is facing
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Setter for the direction.
     *
     * @param dir the direction to set.
     */
    public void setDir(final Direction dir) {
        this.dir = dir;
    }

    /**
     * Getter for the distance walked by the enemy.
     *
     * @return the distance walked by the enemy.
     */
    public long getDistanceWalked() {
        return distance;
    }

    /**
     * Setter for the distance walked by the enemy.
     *
     * @param distance the distance to set
     */
    public void setDistance(final long distance) {
        this.distance = distance;
    }

    /**
     * Returns a copy of this position.
     */
    @Override
    public EnemyPosition clone() {
        return new EnemyPosition(this.getX(), this.getY(), this.getDir(), this.getDistanceWalked());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean equals(final Object other) {
        return other instanceof EnemyPosition
                && super.equals(other)
                && ((EnemyPosition) other).getDir().equals(this.getDir());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getDir());
    }
}
