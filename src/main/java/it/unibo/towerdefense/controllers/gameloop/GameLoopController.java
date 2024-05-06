package it.unibo.towerdefense.controllers.gameloop;

/**
 * Interface that defines GameController methods.
 */
public interface GameLoopController {

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
     * Stops the game loop.
     */
    void stop();

    /**
     * Saves the game.
     */
    void save();

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

    /**
     * Updates the game state.
     */
    void update();

    /**
     * Renders the game state.
     */
    void render();
}
