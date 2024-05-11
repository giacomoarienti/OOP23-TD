package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;

/**
 * Subinterface of EnemyType which also stores model-related statistics.
 *
 * @see EnemyType
 */
abstract class RichEnemyType extends EnemyType {
    /**
     * Getter for the max HP.
     *
     * @return the initial HP for the EnemyType
     */
    abstract int getMaxHP();

    /**
     * Getter for the speed.
     *
     * @return the speed for the EnemyType expressed in
     *         1/LogicPosition.SCALING_FACTOR cells per cycle
     */
    abstract int getSpeed();

    /**
     * Getter for the value to be added when an enemy of the given type is killed.
     *
     * @return the value
     */
    abstract int getValue();

    /**
     * Getter for the powerlevel (roughly hp * speed).
     *
     * @return the powerlevel
     */
    abstract int getPowerLevel();
}
