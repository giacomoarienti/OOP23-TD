package it.unibo.towerdefense.views.window;

import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.graphics.Drawable;
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
     * Get canvas size.
     * @return the size of the canvas
     */
    Size getCanvasSize();

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
     * Updates the contents of the info panel.
     * @param panel the content to display
     */
    void setInfoContent(JPanel panel);

    /**
     * Updates the contents of the buy menu panel.
     * @param panel the content to display
     */
    void setBuyMenuContent(JPanel panel);

    /**
     * Updates the contents of the upgrades panel.
     * @param panel the content to display
     */
    void setUpgradesContent(JPanel panel);

    /**
     * Adds a drawable object to the canvas.
     * @param drawable the drawable object to add
     */
    void submitToCanvas(Drawable drawable);

    /**
     * Adds a list of drawable objects to the canvas.
     * @param drawables the drawable objects list to add
     */
    void submitAllToCanvas(List<? extends Drawable> drawables);

    /**
     * Forces the canvas to rerender.
     */
    void renderCanvas();
}
