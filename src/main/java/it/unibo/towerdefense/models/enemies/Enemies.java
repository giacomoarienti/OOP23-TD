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
     *
     * Will call GameController's advanceWave method the first time it's called with
     * no active wave and no alive enemies.
     */
    void update();

    /**
     * Starts a new wave if one is not currently active and no enemy is alive.
     *
     * Will throw a RuntimeException if a wave is already active or any enemy is alive.
     *
     * @param wave the number of the wave to start
     */
    void spawn(int wave);

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
