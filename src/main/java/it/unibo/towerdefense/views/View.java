package it.unibo.towerdefense.views;

import javax.swing.JPanel;

/**
 * Basic View interface.
 */
public interface View {

    /**
     * Push the view to screen.
     */
    void display();

    /**
     * ContentPane getter.
     * @return the content pane of the View.
     */
    JPanel getContentPane();
}
