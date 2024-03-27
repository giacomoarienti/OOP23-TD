package it.unibo.towerdefense.views;

import javax.swing.JPanel;

/**
 * Basic View interface.
 */
public interface View {

    /**
     * Builds the View and returns its JPanel.
     * @return the JPanel with the view's content.
     */
    JPanel build();
}
