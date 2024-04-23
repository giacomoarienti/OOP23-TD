package it.unibo.towerdefense.views.window;

import java.awt.Image;

import it.unibo.towerdefense.models.engine.Position;
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

    /**
     * Paint an image to canvas.
     * @param img the image to be painted.
     * @param pos the position where the image should be painted.
     * @param size the size of the image.
     */
    void paint(Image img, Position pos, Size size);
}
