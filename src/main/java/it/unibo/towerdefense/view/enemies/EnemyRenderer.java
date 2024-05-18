package it.unibo.towerdefense.view.enemies;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * Renders enemies on the screen.
 */
public interface EnemyRenderer {
    /**
     * Renders the enemies given in the stream argument on the gamerenderer argument.
     *
     * @param gameRenderer the renderer to which drawables should be submitted
     * @param enemies      the enemies to render
     */
    void render(Renderer gameRenderer, Stream<EnemyInfo> enemies);
}
