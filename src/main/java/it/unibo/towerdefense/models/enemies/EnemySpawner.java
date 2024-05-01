package it.unibo.towerdefense.models.enemies;

/**
 * A factory which produces Enemy entities from a given EnemyType
 * with correct starting position and places them in Enemies.
 */
public interface EnemySpawner {
    /**
     * Spawns an Enemy with the specified type.
     * @param t the type of the Enemy to spawn
     * @return the spawn Enemy
     */
    void spawn(RichEnemyType t);
}
