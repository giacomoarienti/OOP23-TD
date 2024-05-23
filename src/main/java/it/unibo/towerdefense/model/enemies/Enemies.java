package it.unibo.towerdefense.model.enemies;

import java.util.Set;

import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * The main interface for the model of this part of the program.
 */
interface Enemies {
    /**
     * Called on every cycle, updates the model.
     *
     * Will call GameController's advanceWave method the first time it's called with
     * no active wave and no alive enemies.
     */
    void update();

    /**
     * Registers an observer to be notified on the death of every enemy.
     *
     * @param o the new observer
     */
    void addDeathObserver(Observer<Enemy> o);

    /**
     * Starts a new wave if one is not currently active and no enemy is alive.
     *
     * Will throw a RuntimeException if a wave is already active or any enemy is alive.
     *
     * @param wave the number of the wave to start
     */
    void spawn(int wave);

    /**
     * Retuns true when there is an active wave.
     *
     * A wave is active when it still has enemies to spawn or not all the spawned
     * enemies are dead.
     *
     * @return true when there is an active wave false otherwise
     */
    boolean isWaveActive();

    /**
     * Returns a Set of all Enemies currently alive.
     *
     * @return a Set of all Enemies currently alive
     */
    Set<? extends Enemy> getEnemies();
}
