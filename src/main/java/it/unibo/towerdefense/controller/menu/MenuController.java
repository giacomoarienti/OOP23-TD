package it.unibo.towerdefense.controller.menu;

import it.unibo.towerdefense.model.saving.Saving;

/**
 * Interface that models controller of menus.
 * Acts like a Mediator for the main Controller so
 * that views only have access to the methods they need.
 */
public interface MenuController {

    /**
     * Start a new game.
     */
    void play();

    /**
     * Start a new game from a saving.
     * @param saving the saving object
     */
    void play(Saving saving);

    /**
     * Quit game.
     */
    void exit();

    /**
     * Display the saving selection view.
     */
    void displaySavings();
}
