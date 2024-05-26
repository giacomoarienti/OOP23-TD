package it.unibo.towerdefense.model.enemies;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;

/**
 * A factory which produces RichEnemy objects from a given RichEnemyType.
 */
interface EnemyFactory {
    /**
     * Spawns an Enemy with the specified type.
     *
     * @param t   the type of the Enemy to spawn
     * @param pos the spawn position of the new enemy
     * @return the spawned Enemy
     */
    RichEnemy spawn(RichEnemyType t, EnemyPosition pos);
}
