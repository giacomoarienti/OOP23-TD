package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.graphics.GameRenderer;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.controllers.map.PathVector;
import it.unibo.towerdefense.controllers.mediator.ControllerMediator;
import it.unibo.towerdefense.models.enemies.Enemies;
import it.unibo.towerdefense.models.enemies.EnemiesImpl;
import it.unibo.towerdefense.models.enemies.Enemy;
import it.unibo.towerdefense.views.enemies.EnemyRenderer;
import it.unibo.towerdefense.views.enemies.EnemyRendererImpl;

/**
 * {@inheritDoc}.
 */
public class EnemyControllerImpl implements EnemyController {

    private final Enemies model;
    private final EnemyRenderer enemyRenderer;

    private Optional<List<Enemy>> lastGivenEnemies = Optional.empty();

    /**
     * Constructor for the class.
     *
     * @param mc the class which acts as a mediator between all different parts of
     *           the application.
     */
    public EnemyControllerImpl(final ControllerMediator mc) {
        final MapController map = mc.getMapController();
        final GameController game = mc.getGameController();
        enemyRenderer = new EnemyRendererImpl(mc.getImageLoader());

        model = new EnemiesImpl(
                (pos, speed) -> convert(map.getNextPosition(LogicalPosition.copyOf(pos), speed)),
                convert(map.getSpawnPosition()).get());

        model.addDeathObserver(e -> {
            game.addMoney(e.getValue());
            if (!model.isWaveActive()) {
                game.advanceWave();
            }
        });
    }

    /**
     * Converts the PathVector given as input to an Optional of an enemyposition
     * which will be empty if pv's distance to end is 0, meaning the enemy has
     * reached the end.
     *
     * @param pv the pathvector to convert
     * @return the corresponding Optional EnemyPosition
     */
    private Optional<EnemyPosition> convert(PathVector pv) {
        return pv.distanceToEnd() > 0
                ? Optional.of(new EnemyPosition(pv.position().getX(), pv.position().getY(), pv.direction(),
                        pv.distanceToEnd()))
                : Optional.empty();
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
    public List<Pair<LogicalPosition, Integer>> getEnemies() {
        lastGivenEnemies = Optional.of(List.copyOf(model.getEnemies()));
        return lastGivenEnemies.get().stream()
            .map(e -> Pair.of(LogicalPosition.copyOf(e.getPosition()), e.getHp()))
            .toList();
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

    /**
     * {@inhertDoc}
     */
    @Override
    public void render(GameRenderer renderer) {
        enemyRenderer.render(renderer,
                model.getEnemies().stream().map(e -> e.info()));
    }
}
