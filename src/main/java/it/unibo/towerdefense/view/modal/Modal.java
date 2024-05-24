package it.unibo.towerdefense.view.modal;

/**
 * Interface that models a Modal window.
 */
public interface Modal {

    /**
     * Shows the modal window.
     */
    void display();

    /**
     * Closes the modal window.
     */
    void close();

    /**
     * Closes the modal window without performing any action.
     */
    void dispose();

    /**
     * Sets the position of the modal window relative to its parent.
     */
    void setPositionRelativeToParent();

    /**
     * Sets the visibility of the modal window.
     * @param b the visibility
     */
    void setVisible(boolean b);
}
