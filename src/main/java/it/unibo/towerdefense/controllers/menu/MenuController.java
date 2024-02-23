package it.unibo.towerdefense.controllers.menu;

/**
 * Interface that models controller of menus.
 */
public interface MenuController {

    /**
     * Displays the game selection view.
     */
    void gameSelection();

    /**
     * Resume the current game.
     */
    void resumeGame();

    /**
     * Pause the game and shows the pause menu.
     */
    void pauseGame();

    /**
     * Quit game.
     */
    void exit();
}
