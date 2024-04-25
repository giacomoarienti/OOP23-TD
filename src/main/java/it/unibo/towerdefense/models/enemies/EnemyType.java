package it.unibo.towerdefense.models.enemies;

/**
 * Interface which models an enemy in its abstract form.
 */
interface EnemyType {
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
