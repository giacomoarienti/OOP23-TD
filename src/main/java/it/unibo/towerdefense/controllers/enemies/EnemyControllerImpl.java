package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.controllers.gameMap.MapController;
import it.unibo.towerdefense.models.enemies.Enemies;
import it.unibo.towerdefense.models.enemies.EnemiesImpl;
import it.unibo.towerdefense.models.enemies.Enemy;
import it.unibo.towerdefense.views.enemies.EnemiesRenderer;
import it.unibo.towerdefense.views.enemies.EnemiesRendererImpl;
import it.unibo.towerdefense.views.window.Window;

/**
 * @inheritDoc .
 */
public class EnemyControllerImpl implements EnemyController {

    private final int width;
    private final int height;
    private final Enemies model;
    private final EnemiesRenderer renderer;
    private final MapController map;
    private Optional<List<Pair<Enemy, Pair<Pair<Integer, Integer>, Integer>>>> lastGivenEnemies = Optional.empty();

    /**
     * Constructor for the controller.
     *
     * @param width  of the game area in cells
     * @param height of the game area in cells
     * @param window handle to be passed to the renderer
     * @param map    to know where to move enemies
     */
    EnemyControllerImpl(final int width, final int height, final Window window, final MapController map) {
        this.width = width;
        this.height = height;
        model = new EnemiesImpl(width, height, map);
        renderer = new EnemiesRendererImpl(window);
        this.map = map;
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void update() {
        model.move();
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void render() {
        renderer.render(model.getEnemiesInfo());
    }

    /**
     * @inheritDoc .
     */
    @Override
    public List<Pair<Pair<Integer, Integer>, Integer>> getEnemies() {
        lastGivenEnemies = Optional.of(model.getEnemies().stream()
                .map(e -> Pair.of(e, Pair.of(e.getPosition().asPair(), e.getHp()))).toList());
        return lastGivenEnemies.get().stream().map(p -> p.getRight()).toList();
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void hurtEnemies(final Set<Pair<Integer, Integer>> indexes) {
        if (lastGivenEnemies.isEmpty()) {
            throw new IllegalStateException();
        }
        for (Pair<Integer, Integer> element : indexes) {
            lastGivenEnemies.get().get(element.getLeft()).getLeft().hurt(element.getRight());
        }
    }

}
