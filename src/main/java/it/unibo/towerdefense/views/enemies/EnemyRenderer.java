package it.unibo.towerdefense.views.enemies;

import java.util.List;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.graphics.GameRenderer;

/**
 * Renders enemies on the screen.
 */
public interface EnemyRenderer {
    /**
     * Renders the enemies given in the list argument on the gamerenderer argument.
     *
     * @param gameRenderer the renderer to which drawables should be submitted
     * @param enemies      the enemies to render
     */
    void render(GameRenderer gameRenderer, List<EnemyInfo> enemies);
}
