package it.unibo.towerdefense.controller.app;

import it.unibo.towerdefense.model.savingloader.saving.Saving;
import it.unibo.towerdefense.view.modal.ModalContent;

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
     * Starts the game from select saving.
     * @param saving the saving object
     */
    void start(Saving saving);

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
     * Get the player name.
     * @return the player name
     */
    String getPlayerName();

    /**
     * Displays a modal with given content.
     * @param title the title of the modal
     * @param content the content of the modal
     */
    void displayModal(String title, ModalContent content);
}
