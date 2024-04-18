package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * Interface of Enemy.
 */
public interface Enemy {

    /**
     * Hurts the enemy.
     * @param amount how much
     * @throws IllegalArgumentException if amount < 0
     */
    void hurt(int amount);

    /**
     *  Moves the enemy to the desired position.
     *
     * @param x x
     * @param y y
     */
    void move(int x, int y);

    /**
     * Returns the HP.
     * @return the hp
     */
    int getHp();

    /**
     * Returns the speed.
     * @return th speed
     */
    int getSpeed();

    /**
     * Returns the enemy's EnemyInfo.
     *
     * @return the enemy's EnemyInfo.
     */
    EnemyInfo info();

    /**
     * Returns the enemy's EnemyPosition.
     *
     * @return the enemy's EnemyPosition.
     */
    LogicalPosition getPosition();

}
