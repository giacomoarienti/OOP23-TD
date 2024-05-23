package it.unibo.towerdefense.model.enemies;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;

/**
 * The single Enemy as seen from outside this package.
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
     * Returns the HP.
     *
     * @return the hp
     */
    int getHp();

    /**
     * Returns the speed of the enemy in 1 / LogicalPosition.SCALING_FACTOR of a
     * cell.
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
     * Returns the enemy's LogicalPosition.
     *
     * @return the enemy's LogicalPosition.
     */
    EnemyPosition getPosition();

    /**
     * Returns the enemy's EnemyInfo.
     *
     * @return the enemy's EnemyInfo.
     */
    EnemyInfo info();

    /**
     * Returns wheter the enemy is dead or not.
     *
     * @return wheter the enemy is dead or not
     */
    boolean isDead();
}
