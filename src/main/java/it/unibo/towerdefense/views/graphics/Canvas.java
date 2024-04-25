package it.unibo.towerdefense.views.graphics;

public interface Canvas {

    /**
     * Force the canvas to repaint.
     */
    void render();

    /**
     * Submit a drawable to the canvas.
     * @param drawable the drawable to submit
     */
    void submit(Drawable drawable);

    /**
     * Submit a drawable to the canvas to be drawn as first element.
     * @param drawable the drawable to submit
     */
    void submitBackground(Drawable drawable);

}