package it.unibo.towerdefense.controller.menu;

import it.unibo.towerdefense.model.saves.Save;

/**
 * Interface that models controller of menus.
 * Acts like a Mediator for the main Controller so
 * that views only have access to the methods they need.
 */
public interface StartMenuController {

    /**
     * RUn the controller and display the StartMenu.
     */
    void run();

    /**
     * Start a new game.
     */
    void play();

    /**
     * Start a new game from a save.
     * @param save the save object
     */
    void play(Save save);

    /**
     * Quit game.
     */
    void exit();

    /**
     * Display the save selection view.
     */
    void displaySaves();

    /**
     * Display the scoreboard.
     */
    void displayScoreboard();
}
