package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.controllers.Controller;

/**
 * Interface that defines GameController methods.
 */
public interface GameController extends Controller {

    /**
     * Starts the game loop.
     */
    void start();

    /**
     * Sets gameState to PLAYING.
     */
    void resume();

    /**
     * Sets gameState to PAUSE.
     */
    void pause();

    /**
     * Save the current game.
     * @return true if the game was saved
     */
    boolean save();

    /**
     * Gets current game speed.
     * @return game speed
     */
    double getGameSpeed();

    /**
     * Returns if the game loop should run.
     * @return true if game is running
     */
    boolean isRunning();

    /**
     * Return if the process should terminate.
     * @return true if the game loop should stop.
     */
    boolean isTerminated();
}
