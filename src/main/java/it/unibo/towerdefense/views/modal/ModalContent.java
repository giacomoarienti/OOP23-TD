package it.unibo.towerdefense.views.modal;

import javax.swing.JPanel;

/**
 * Interface that models the content of a Modal window.
 */
public interface ModalContent {

    /**
     * Builds the View and returns its JPanel.
     * @param onClose the action to be performed to close.
     * @return the JPanel with the view's content.
     */
    JPanel build(Runnable onClose);
}
