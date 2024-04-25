package it.unibo.towerdefense.models.enemies;

/**
 * A factory which produces Enemy entities from a given EnemyType.
 */
public interface EnemyFactory {
    /**
     * Creates an Enemy with the specified type.
     * @param t the type of the Enemy to create
     * @return the created Enemy
     */
    Enemy build(EnemyType t);
}
