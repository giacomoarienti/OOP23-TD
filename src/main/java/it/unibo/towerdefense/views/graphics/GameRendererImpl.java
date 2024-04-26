package it.unibo.towerdefense.views.graphics;

import java.util.List;

import it.unibo.towerdefense.views.View;
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
    public void renderInfo(final View view) {
        this.window.setInfoContent(view.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderBuyMenu(final View view) {
        this.window.setBuyMenuContent(view.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderUpgrades(final View view) {
        this.window.setUpgradesContent(view.build());
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
    public void submitAllToCanvas(final List<Drawable> drawables) {
        this.window.submitAllToCanvas(drawables);
    }
}
