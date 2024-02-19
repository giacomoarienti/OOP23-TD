package it.unibo.towerdefense.views;

import javax.swing.JPanel;

public interface BaseView extends View {

    /* update the content of the frame */
    void setContet(JPanel panel);
    
    /* return screen dimensions */
    int getWidth();

    int getHeight();
}
