package it.unibo.towerdefense.controllers.menu;

/**
 * Interface that models controller of menus.
 */
public interface MenuController {

    /**
     * Start a new game.
     */
    void play();

    /**
     * Pause the current game.
     */
    void pause();

    /**
     * Resume the current game.
     */
    void resume();

    /**
     * Quit game.
     */
    void exit();

    /**
     * Display the start menu.
     */
    void displayStartMenu();

    /**
     * Display the saving selection view.
     */
    void displaySavingSelection();
}
