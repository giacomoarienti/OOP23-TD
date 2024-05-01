package it.unibo.towerdefense.controllers.enemies;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * Data about an Enemy to be passed around.
 */
public interface EnemyInfo {

    /**
     * Getter for the LogicalPosition of the Enemy.
     *
     * @return the position of the Enemy
     */
    LogicalPosition getPos();

    /**
     * Getter for the Enemy's hp.
     *
     * @return the hp
     */
    Integer getHp();

    /**
     * Getter for the Enemy's EnemyType.
     * @return the EnemyType of the enemy.
     */
    EnemyType getType();
}
