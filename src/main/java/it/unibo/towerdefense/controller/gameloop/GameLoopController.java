package it.unibo.towerdefense.controller.gameloop;

/**
 * Interface that defines GameController methods.
 */
public interface GameLoopController {

    /**
     * Starts the game loop.
     */
    void start();

    /**
     * Stops the game loop.
     */
    void stop();

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
