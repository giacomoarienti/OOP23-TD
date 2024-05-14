package it.unibo.towerdefense.models.enemies;

/**
 * A factory which produces Enemy entities from a given EnemyType.
 */
interface EnemyFactory {
    /**
     * Spawns an Enemy with the specified type.
     *
     * @param t the type of the Enemy to spawn
     * @return the spawned Enemy
     */
    RichEnemy spawn(RichEnemyType t);
}
