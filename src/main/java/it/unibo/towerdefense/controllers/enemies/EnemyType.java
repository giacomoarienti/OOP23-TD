package it.unibo.towerdefense.controllers.enemies;

/**
 * At the highest level of abstraction an enemy type is defined by its level and
 * archetype.
 *
 * @see EnemyLevel
 * @see EnemyArchetype
 */
public interface EnemyType {
    /**
     * Getter for the level that characterizes this EnemyType.
     *
     * @return the corresponding EnemyLevel
     */
    EnemyLevel level();

    /**
     * Getter for the archetype that characterizes this EnemyType.
     *
     * @return the corresponding EnemyArchetype
     */
    EnemyArchetype type();
}
