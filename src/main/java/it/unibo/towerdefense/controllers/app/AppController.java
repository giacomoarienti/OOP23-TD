package it.unibo.towerdefense.controllers.app;

/**
 * Interface that defines GameController methods.
 */
public interface AppController {

    /**
     * Run the application.
     */
    void run();

    /**
     * Starts the game.
     */
    void start();

    /**
     * Resumes the game.
     */
    void resume();

    /**
     * Pauses the game.
     */
    void pause();

    /**
     * Save and exit the game.
     */
    void saveAndExit();

    /**
     * Quits the game.
     */
    void exit();
}
