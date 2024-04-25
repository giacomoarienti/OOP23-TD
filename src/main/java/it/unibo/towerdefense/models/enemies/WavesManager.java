package it.unibo.towerdefense.models.enemies;

/**
 * Object which manages the progress of the waves.
 *
 * It is instructed to start a new wave with a call to the spawn method.
 * It communicates the end of the wave to an observer.
 */
public interface WavesManager {
    /**
     * Method called on each cycle to advance the status of the waves.
     */
    void update();

    /**
     * Starts spawning the wave passed as argument.
     *
     * @param wave the number of the wave to start
     */
    void spawn(int wave);
}
