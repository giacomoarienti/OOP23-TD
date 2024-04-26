package it.unibo.towerdefense.controllers;

import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Interface that models a standard Controller.
 */
public interface Controller {

    /**
     * Updates the state of the relative model.
     */
    void update();

    /**
     * Renders on the screen based on current state.
     */
    void render(GameRenderer renderer);

}
