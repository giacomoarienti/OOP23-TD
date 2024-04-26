package it.unibo.towerdefense.views.window;

import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.views.modal.ModalContent;

/**
 * Interface that defines the Window methods.
 */
public interface Window {

    /**
     * Push content to screen.
     */
    void display();

    /**
     * Close the window.
     */
    void close();

    /**
     * Get the size of the window.
     * @return the size of the window
     */
    Size getSize();

    /**
     * Display a error message.
     * @param message the message to display
     */
    void displayError(String message);

    /**
     * Display a modal window.
     * @param title the title of the modal
     * @param content the content of the modal
     */
    void displayModal(String title, ModalContent content);
}
