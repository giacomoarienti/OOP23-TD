package it.unibo.towerdefense.view.enemies;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * {@InheritDoc}.
 */
public class EnemyRendererImpl implements EnemyRenderer {

    private final Renderer renderer;
    private final EnemyGraphics graphics;

    /**
     * Loads all the images from disk and saves them in a structure for quick access.
     *
     * @param loader the ImageLoader to load the images.
     */
    public EnemyRendererImpl(final Renderer renderer) {
        this.renderer = renderer;
        this.graphics = new EnemyGraphicsImpl(renderer.getImageLoader());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void render(final Stream<EnemyInfo> enemies) {
        renderer.submitAllToCanvas(enemies
            .parallel()
            .sorted((e1, e2) -> Long.compare(e1.pos().getDistanceWalked(), e2.pos().getDistanceWalked()))
            .flatMap(e -> graphics.getDrawablesFor(e))
            .toList());
    }
}
