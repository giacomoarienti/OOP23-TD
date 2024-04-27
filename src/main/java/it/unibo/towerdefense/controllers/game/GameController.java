package it.unibo.towerdefense.controllers.game;

/**
 * Interface that defines GameController methods.
 */
public interface GameController {

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
     * Increase wave number.
     */
    void advanceWave();

    /**
     * Gets current game speed.
     * @return game speed
     */
    double getGameSpeed();

    /**
     * Gets the current wave number.
     * @return wave number
     */
    int getWave();

    /**
     * Get the money of the player.
     * @return the amount of money
     */
    int getMoney();

    /**
     * Get lives of the player.
     * @return the amount of lives
     */
    int getLives();

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
