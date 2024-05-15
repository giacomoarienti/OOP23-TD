package it.unibo.towerdefense.controller.gamelauncher;

import java.util.List;

import it.unibo.towerdefense.commons.engine.Size;

/**
 * Interface that defines the GameLauncherController methods.
 * Acts like a Logic for the GameLauncherView.
 */
public interface GameLauncherController {

    /**
     * Run the game launcher and display its view.
     */
    void run();

    /**
     * Get available resolutions.
     * @return a list of resolutions
     */
    List<Size> getResolutions();

    /**
     * Select the resolution of the game.
     * @param selection the index of the resolution
     */
    void selectResolution(int selection);

    /**
     * Saves player's name.
     * @param name the player's name
     */
    void setPlayerName(String name);

    /**
     * Start the game.
     */
    void startGame();
}
