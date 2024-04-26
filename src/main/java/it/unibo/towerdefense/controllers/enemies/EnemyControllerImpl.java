package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.models.enemies.EnemyCollection;
import it.unibo.towerdefense.models.enemies.EnemyCollectionImpl;
import it.unibo.towerdefense.models.enemies.Enemy;
import it.unibo.towerdefense.models.enemies.SimpleWavesManager;
import it.unibo.towerdefense.models.enemies.WavesManager;
import it.unibo.towerdefense.views.enemies.EnemiesRenderer;
import it.unibo.towerdefense.views.enemies.EnemiesRendererImpl;
import it.unibo.towerdefense.views.window.Window;

/**
 * @inheritDoc .
 */

public class EnemyControllerImpl implements EnemyController {

    private final EnemyCollection enemies;
    private final EnemiesRenderer renderer;
    private final WavesManager waves;

    private Optional<List<Enemy>> lastGivenEnemies = Optional.empty();

    /**
     * Constructor for the controller.
     *
     * @param width  of the game area in cells
     * @param height of the game area in cells
     * @param window handle to be passed to the renderer
     * @param map    to know where to move enemies
     */
    EnemyControllerImpl(final Window window, final MapController map, final GameController gc) {
        enemies = new EnemyCollectionImpl(map);
        renderer = new EnemiesRendererImpl(window);
        waves = new SimpleWavesManager(enemies, gc, new LogicalPosition(0, 0), null); //map.getStartingPos
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void update() {
        enemies.move();
        waves.update();
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void render() {
        renderer.render(enemies.getEnemiesInfo());
    }

    /**
     * @inheritDoc .
     */
    @Override
    public List<Pair<LogicalPosition, Integer>> getEnemies() {
        lastGivenEnemies = Optional.of(enemies.getEnemies().stream().toList());
        return lastGivenEnemies.get().stream().map(e -> Pair.of(e.getPosition(), e.getHp())).toList();
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void hurtEnemies(final Map<Integer, Integer> which) {
        if (lastGivenEnemies.isEmpty()) {
            throw new IllegalStateException("getEnemies has not been called yet");
        }else{
            for (Entry<Integer, Integer> element : which.entrySet()) {
                lastGivenEnemies.get().get(element.getKey()).hurt(element.getValue());
            }
        }
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void spawn(int wave){
        throw new UnsupportedOperationException();
    }

}
