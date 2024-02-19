package it.unibo.towerdefense.views.window;

import javax.swing.JPanel;

/**
 * Interface that defines the Window methods.
 */
public interface Window {

    /**
     * Sets the content pane of the Window.
     * @param panel to be set as content
     */
    void setBody(JPanel panel);
    /**
     * Width getter.
     * @return Frame width
     */
    int getWidth();
    /**
     * Height getter.
     * @return Frame height
     */
    int getHeight();
    /**
     * Push content to screen.
     */
    void display();
}
