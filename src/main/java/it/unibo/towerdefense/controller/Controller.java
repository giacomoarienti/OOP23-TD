package it.unibo.towerdefense.controller;

import it.unibo.towerdefense.view.graphics.GameRenderer;

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
     * @param renderer the game renderer
     */
    void render(GameRenderer renderer);

}
