package it.unibo.towerdefense.views.enemies;

import java.util.Collection;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.views.Renderer;

/**
 * Renders enemies on the screen.
 */
public interface EnemiesRenderer extends Renderer {
    /**
     * Renders the enemies passed as argument.
     *
     * @param l Collection of EnemyInfo about enemies to be rendered.
     */
    void render(Collection<EnemyInfo> l);
}
