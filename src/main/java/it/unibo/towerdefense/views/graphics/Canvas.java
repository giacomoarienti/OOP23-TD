package it.unibo.towerdefense.views.graphics;

import java.util.List;

public interface Canvas {

    /**
     * Force the canvas to repaint.
     */
    void render();

    /**
     * Submit a drawable to the canvas queue.
     * @param drawable the drawable to submit
     */
    void submit(Drawable drawable);

    /**
     * Submit a list of drawebles to the canvas queue.
     * @param drawables the list of drawables to submit
     */
    void submitAll(List<Drawable> drawables);

    /**
     * Submit a drawable to the canvas to be drawn as
     * first element.
     * @param drawable the drawable to submit
     */
    void submitBackground(Drawable drawable);

    /**
     * Submit a list of drawable to the canvas to be drawn
     * as first elements.
     * @param drawables the list of drawables to submit
     */
    void submitBackgroundAll(List<Drawable> drawables);

}