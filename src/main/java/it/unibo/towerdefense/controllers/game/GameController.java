package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.controllers.Controller;

/**
 * Interface that defines GameController methods.
 */
public interface GameController extends Controller {

    /**
     * Sets gameState to PLAYING.
     */
    void resumeGame();

    /**
     * Sets gameState to PAUSE.
     */
    void pauseGame();

    /**
     * Get current game speed.
     * @return game speed
     */
    double getGameSpeed();

    /**
     * Check if game is running.
     * @return true if game is running
     */
    boolean isRunning();

    /**
     * Quits the game.
     */
    void exit();
}
