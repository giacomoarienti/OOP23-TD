package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.function.Predicate;

/**
 * Responsible of maintaining a List of EnemyTypes currently in the game.
 */
public interface EnemyCatalogue {
    /**
     * Returns a list of all EnemyTypes in the game.
     *
     * @return a list of all EnemyTypes in the game
     */
    public List<RichEnemyType> getEnemyTypes();

    /**
     * Returns a list of all EnemyTypes in the game for which the predicate tests
     * true.
     *
     * @return a list of all EnemyTypes in the game for which the predicate tests
     *         true
     */
    public List<RichEnemyType> getEnemyTypes(Predicate<? super RichEnemyType> test);
}
