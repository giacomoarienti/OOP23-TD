package it.unibo.towerdefense.views;

import javax.swing.JPanel;

/**
 * Basic View interface.
 */
public interface View {

    /**
     * Adds itself to the given container.
     * @param container panel on which the
     * view will be added
     */
    void display(JPanel container);
}
