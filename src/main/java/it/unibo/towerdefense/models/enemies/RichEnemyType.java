package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * Interface which models an enemy in its abstract form.
 */
interface RichEnemyType extends EnemyType {
    /**
     * Getter for the max HP.
     *
     * @return the initial HP for the EnemyType
     */
    int getMaxHP();

    /**
     * Getter for the speed.
     *
     * @return the speed for the EnemyType expressed in 1/SCALING_FACTOR cells per cycle
     */
    int getSpeed();
}
