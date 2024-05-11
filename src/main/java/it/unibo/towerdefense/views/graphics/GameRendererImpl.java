package it.unibo.towerdefense.views.graphics;

import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.utils.images.ImageLoader;
import it.unibo.towerdefense.views.window.Window;

/**
 * Implementation of the GameRenderer interface.
 */
public class GameRendererImpl implements GameRenderer {

    private final Window window;
    private final ImageLoader imageLoader;

    /**
     * Constructor with the map's Size, Window instance.
     * @param mapSize the size of the map
     * @param window the window instance
     */
    public GameRendererImpl(final Size mapSize, final Window window) {
        this.window = window;
        // initialize the image loader
        this.imageLoader = new ImageLoader(
            mapSize.getWidth() / window.getCanvasSize().getWidth()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderInfo(final JPanel panel) {
        this.window.setInfoContent(panel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderBuyMenu(final JPanel panel) {
        this.window.setBuyMenuContent(panel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderUpgrades(final JPanel panel) {
        this.window.setUpgradesContent(panel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderCanvas() {
        this.window.renderCanvas();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitToCanvas(final Drawable drawable) {
        this.window.submitToCanvas(drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitAllToCanvas(final List<? extends Drawable> drawables) {
        this.window.submitAllToCanvas(drawables);
    }
}
