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
     * Quits the game.
     */
    void exit();
}
