package it.unibo.towerdefense.view.graphics;

import java.util.List;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Interface that defines the Canvas methods.
 */
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
     * Submit a list of drawables to the canvas queue.
     * @param drawables the list of drawables to submit
     */
    void submitAll(List<? extends Drawable> drawables);

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
    void submitBackgroundAll(List<? extends Drawable> drawables);

    /**
     * Add an observer to the canvas.
     * The observer will be notified when the canvas is clicked.
     * @param observer the observer to add
     */
    void addClickObserver(Observer<Position> observer);

    /**
     * Set the map size.
     * @param mapSize the size of the map
     */
    void setMapSize(Size mapSize);

    /**
     * Get the size of the canvas.
     * @return the true size of the canvas
     */
    Size getCanvasSize();
}
