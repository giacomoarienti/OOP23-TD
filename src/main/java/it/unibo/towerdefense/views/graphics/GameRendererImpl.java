package it.unibo.towerdefense.views.graphics;

import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.views.window.Window;

/**
 * Implementation of the GameRenderer interface.
 */
public class GameRendererImpl implements GameRenderer {

    private final Window window;

    /**
     * Constructor with Window instance.
     * @param window the window instance
     */
    public GameRendererImpl(final Window window) {
        this.window = window;
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
