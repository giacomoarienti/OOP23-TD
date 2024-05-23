package it.unibo.towerdefense.model.enemies;

import java.util.Set;

import it.unibo.towerdefense.model.Manager;

/**
 * The class which binds this part of the model to the others.
 */
public interface EnemiesManager extends Manager {
    /**
     * Returns a Set of all Enemies currently alive.
     *
     * @return a Set of all Enemies currently alive
     */
    Set<? extends Enemy> getEnemies();

    /**
     * Starts a new wave if one is not currently active and no enemy is alive.
     *
     * Will throw a RuntimeException if a wave is already active or any enemy is alive.
     *
     * @param wave the number of the wave to start
     */
    void spawn(int wave);

    /**
     * Called on every cycle, updates the model.
     *
     * Will call GameController's advanceWave method the first time it's called with
     * no active wave and no alive enemies.
     */
    void update();
}
