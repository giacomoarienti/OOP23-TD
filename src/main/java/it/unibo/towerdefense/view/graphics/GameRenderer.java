package it.unibo.towerdefense.view.graphics;

import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.commons.utils.images.ImageLoader;

/**
 * Interface that models the game renderer.
 * It acts as a Proxy for the Window class.
 */
public interface GameRenderer {

    /**
     * Returns the singleton instance of imageLoader.
     * @return the imageLoader.
     */
    ImageLoader getImageLoader();

    /**
     * Adds view contents to the info panel.
     * @param panel the panel to display
     */
    void renderInfo(JPanel panel);

    /**
     * Adds view contents to the buy menu panel.
     * @param panel the panel to display
     */
    void renderBuyMenu(JPanel panel);

    /**
     * Adds view contents to the upgrades panel.
     * @param panel the panel to display
     */
    void renderUpgrades(JPanel panel);

    /**
     * Adds a drawable object to the canvas.
     * @param drawable the drawable object to paint
     */
    void submitToCanvas(Drawable drawable);

    /**
     * Adds a list of drawable objects to the canvas.
     * @param drawables the drawable objects list to paint
     */
    void submitAllToCanvas(List<? extends Drawable> drawables);

    /**
     * Submit a list of drawable to the canvas to be drawn
     * as first elements.
     * @param drawables the list of drawables to submit
     */
    void submitBackgroundAllToCanvas(List<? extends Drawable> drawables);

    /**
     * Forces the canvas to be rendered.
     */
    void renderCanvas();
}
