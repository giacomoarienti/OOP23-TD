package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * The single Enemy.
 */
public interface Enemy {

    /**
     * Hurts the enemy.
     *
     * Will throw a Runtime Exception if amount < 0.
     *
     * @param amount how much
     */
    void hurt(int amount);

    /**
     * Sets the enemy's position to be the same of the one passed as an argument.
     *
     * @param pos the new position
     */
    void move(final LogicalPosition pos);

    /**
     * Moves the enemy to the desired position.
     *
     * @param x x
     * @param y y
     */
    void move(int x, int y);

    /**
     * Returns the HP.
     *
     * @return the hp
     */
    int getHp();

    /**
     * Returns the speed.
     *
     * @return the speed
     */
    int getSpeed();

    /**
     * Returns the value of the enemy.
     *
     * @return the value of the enemy
     */
    int getValue();


    /**
     * Returns the enemy's EnemyPosition.
     *
     * @return the enemy's EnemyPosition.
     */
    LogicalPosition getPosition();

    /**
     * Returns the enemy's EnemyInfo.
     *
     * @return the enemy's EnemyInfo.
     */
    EnemyInfo info();

}
