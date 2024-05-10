package it.unibo.towerdefense.models.enemies;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Responsible of maintaining implementation-related infos about EnemyTypes
 * currently in the game.
 */
public interface EnemyCatalogue {
    /**
     * Returns a list of all RichEnemyTypes in the game.
     *
     * @return a list of all RichEnemyTypes in the game
     */
    Set<RichEnemyType> getEnemyTypes();

    /**
     * Returns a list of all RichEnemyTypes in the game for which the predicate tests
     * true.
     *
     * @param test the predicate to test on all RichEnemyTypes
     * @return a list of all RichEnemyTypes in the game for which the predicate tests
     *         true
     */
    Set<RichEnemyType> getEnemyTypes(Predicate<? super RichEnemyType> test);
}
