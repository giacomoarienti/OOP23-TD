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
     * Gets current game speed.
     * @return game speed
     */
    double getGameSpeed();

    /**
     * Checks if game is running.
     * @return true if game is running
     */
    boolean isRunning();

    /**
     * Displays the game selection view.
     */
    void gameSelection();

    /**
     * Quits the game.
     */
    void exit();
}
