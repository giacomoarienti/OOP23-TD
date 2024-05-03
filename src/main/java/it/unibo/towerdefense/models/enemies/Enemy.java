package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.commons.Observer;
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
     * Kills the enemy.
     *
     * Performs all the associated tasks and notifies all observers.
     */
    void die();

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

    /**
     * Adds the given Observer to those
     * that will be notified on the enemy's death.
     */
    void addDeathObserver(Observer<Enemy> observer);

}
