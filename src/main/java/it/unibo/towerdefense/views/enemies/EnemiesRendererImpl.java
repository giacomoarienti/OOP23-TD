package it.unibo.towerdefense.views.enemies;

import java.util.Collection;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.views.window.Window;

/**
 * {@InheritDoc}.
 */
public class EnemiesRendererImpl implements EnemiesRenderer {

    /**
     * Constructor for the class.
     *
     * @param window handle to graphics
     */
    public EnemiesRendererImpl(final Window window) {
    }

    /**
     * @InheritDoc .
     */
    @Override
    public void render(final Collection<EnemyInfo> l) {
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    /**
     * No enemies to render .
     */
    @Override
    public void render() {
    }
}
