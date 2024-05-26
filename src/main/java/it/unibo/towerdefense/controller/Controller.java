package it.unibo.towerdefense.controller;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.saves.Save;

/**
 * Interface that models a standard Controller.
 */
public interface Controller {

    /**
     * Launch the game.
     */
    void launch();

    /**
     * Starts the application and displays the start-menu.
     * @param playerName the player name
     * @param resolution the resolution of the screen
     */
    void run(String playerName, Size resolution);

    /**
     * Starts the game.
     */
    void start();

    /**
     * Starts the game from select save.
     * @param save the save object
     */
    void start(Save save);

    /**
     * Save the game.
     */
    void save();

    /**
     * Save and exit the game.
     */
    void saveAndExit();

    /**
     * Quits the game.
     */
    void exit();

    /**
     * Stops the game from updating.
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
     * Displays the save selection view.
     */
    void displaySaves();

    /**
     * Displays the scoreboard view.
     */
    void displayScoreboard();

    /**
     * Updates the state of the relative model.
     */
    void update();

    /**
     * Renders the state of the relative model.
     */
    void render();
}
