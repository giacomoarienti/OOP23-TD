package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.models.enemies.Enemies;
import it.unibo.towerdefense.models.enemies.EnemiesImpl;
import it.unibo.towerdefense.models.enemies.Enemy;
import it.unibo.towerdefense.views.window.Window;

/**
 * {@inheritDoc}.
 * Il controller funge anche da [Pattern]Mediator con gli altri elementi
 * dell'applicazione.
 */
public class EnemyControllerImpl implements EnemyController {

    private final Enemies model;

    private Optional<List<Enemy>> lastGivenEnemies = Optional.empty();

    /**
     * Constructor for the controller.
     *
     * @param window handle to be passed to the renderer
     * @param map    to know where to move enemies
     * @param gc     to signal wave advancement and money attribution
     */
    EnemyControllerImpl(final Window window, final MapController map, final GameController gc) {
        model = new EnemiesImpl(map, gc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        model.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        // renderer.render(model.getEnemiesInfo());
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<LogicalPosition, Integer>> getEnemies() {
        lastGivenEnemies = Optional.of(List.copyOf(model.getEnemies()));
        return lastGivenEnemies.get().stream().map(e -> Pair.of(e.getPosition(), e.getHp())).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hurtEnemies(final Map<Integer, Integer> which) {
        if (lastGivenEnemies.isEmpty()) {
            throw new IllegalStateException("getEnemies has not been called yet");
        } else {
            for (Entry<Integer, Integer> element : which.entrySet()) {
                lastGivenEnemies.get().get(element.getKey()).hurt(element.getValue());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void spawn(final int wave) {
        model.spawn(wave);
    }

}
