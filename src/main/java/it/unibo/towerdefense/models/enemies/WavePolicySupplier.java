package it.unibo.towerdefense.models.enemies;

import java.util.function.Predicate;

import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * Determines how waves are formed (what type and how many enemies they contain
 * and their position in time).
 */
public interface WavePolicySupplier {
    /**
     * Returns a predicate which tests true for admitted Types.
     *
     * @param wave the wave about which information is asked
     * @return the predicate
     */
    Predicate<EnemyType> getPredicate(Integer wave);

    /**
     * Returns how many enemies the wave should contain.
     *
     * @param wave the wave about which information is asked
     * @return the number of enemies
     */
    Integer getLength(Integer wave);

    /**
     * Returns how many cycles pass between two spawns (counting the cycle in which
     * the first enemy spawns).
     *
     * @param wave the wave about which information is asked
     * @return the number of cycles
     */
    Integer getCyclesPerSpawn(Integer wave);
}
