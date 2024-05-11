package it.unibo.towerdefense.controllers.enemies;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Data about an Enemy to be passed around (DTO).
 */
public interface EnemyInfo {

    /**
     * Getter for the LogicalPosition of the Enemy.
     *
     * @return the position of the Enemy
     */
    LogicalPosition pos();

    /**
     * Getter for the Enemy's hp.
     *
     * @return the hp
     */
    Integer hp();

    /**
     * Getter for the Enemy's EnemyType.
     * @return the EnemyType of the enemy.
     */
    EnemyType type();
}
