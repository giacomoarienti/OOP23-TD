package it.unibo.towerdefense.views.window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.towerdefense.views.modal.ModalContent;

import java.awt.Canvas;

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
     * Return the JFrame where game is displayed.
     * @return the JFrame
     */
    JFrame getFrame();

    /**
     * Return the Canvas  where game is displayed.
     * @return the Canvas
     */
    Canvas getCanvas();

    /**
     * Return the side Buy menu's container.
     * @return the JPanel menu container.
     */
    JPanel getBuyMenuContainer();

    /**
     * Return the side Upgrade menu's container.
     * @return the JPanel menu container.
     */
    JPanel getUpgradeMenuContainer();

    /**
     * Return the top info container.
     * @return JPanel info container.
     */
    JPanel getInfoContainer();
}
