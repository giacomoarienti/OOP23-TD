package it.unibo.towerdefense.view.enemies;

import java.util.stream.Stream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * {@InheritDoc}.
 */
public class EnemyRendererImpl implements EnemyRenderer {

    private final Renderer renderer;
    private final EnemyGraphics graphics;

    /**
     * Loads all the images from disk and saves them in a structure for quick
     * access.
     *
     * @param renderer the Renderer on which to render enemies and from which to
     *                 retrieve an ImageLoader.
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "Renderer is intentionally mutable and safe to store."
    )
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
