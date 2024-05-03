package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Set;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * The main interface for the model of this part of the program.
 */
public interface Enemies {
    /**
     * Called on every cycle, updates the model.
     */
    void update();

    /**
     * Starts a new wave if one is not currently active.
     *
     * Will throw a RuntimeException if a wave is already active.
     *
     * @param wave the number of the wave to start
     */
    void spawn(final int wave);

    /**
     * Returns a Set of all Enemies currently alive.
     *
     * @return a Set of all Enemies currently alive
     */
    Set<Enemy> getEnemies();

    /**
     * Returns a list containing DTOs for all Enemies currently alive.
     *
     * @return a list with an EnemyInfo for each enemy alive
     */
    List<EnemyInfo> getEnemiesInfo();
}
