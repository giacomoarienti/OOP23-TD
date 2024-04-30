package it.unibo.towerdefense.controllers.app;

import it.unibo.towerdefense.views.modal.ModalContent;

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

    /**
     * Displays a modal with given content.
     * @param title the title of the modal
     * @param content the content of the modal
     */
    void displayModal(String title, ModalContent content);
}
