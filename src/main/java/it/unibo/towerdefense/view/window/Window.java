package it.unibo.towerdefense.view.window;

import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.view.graphics.Drawable;
import it.unibo.towerdefense.view.modal.ModalContent;

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
     * Get the resolution of the window.
     * @return the resolution of the window
     */
    Size getResolution();

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
     * All other open modals will be hidden until this one is closed.
     * @param title the title of the modal
     * @param content the content of the modal
     */
    void displayModal(String title, ModalContent content);

    /**
     * Updates the contents of the game panel.
     * @param panel the content to display
     */
    void setGameContent(JPanel panel);

    /**
     * Updates the contents of the controls panel.
     * @param panel the content to display
     */
    void setControlsContent(JPanel panel);

    /**
     * Updates the contents of the buy menu panel.
     * @param panel the content to display
     */
    void setBuyMenuContent(JPanel panel);

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
     * Submit a list of drawable to the canvas to be drawn
     * as first elements.
     * @param drawables the list of drawables to submit
     */
    void submitBackgroundAll(List<? extends Drawable> drawables);

    /**
     * Forces the canvas to rerender.
     */
    void renderCanvas();

    /**
     * Adds an observer to the canvas click event.
     * @param observer the observer to add
     */
    void addCanvasClickObserver(Observer<Position> observer);

    /**
     * Sets the map size.
     * @param mapSize the size of the map
     */
    void setMapSize(Size mapSize);

    /**
     * Clear the canvas queue.
     */
    void clearCanvasQueue();

    /**
     * Close all open modal.
     */
    void closeModals();
}
