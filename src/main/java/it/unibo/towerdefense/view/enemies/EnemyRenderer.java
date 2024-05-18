package it.unibo.towerdefense.view.enemies;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;

/**
 * Renders enemies on the screen.
 */
public interface EnemyRenderer {
    /**
     * Renders the enemies given in the stream argument.
     *
     * @param enemies      the enemies to render
     */
    void render(Stream<EnemyInfo> enemies);
}
