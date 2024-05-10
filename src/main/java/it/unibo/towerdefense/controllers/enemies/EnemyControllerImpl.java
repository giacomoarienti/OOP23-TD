package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.mediator.ControllerMediator;
import it.unibo.towerdefense.models.enemies.Enemies;
import it.unibo.towerdefense.models.enemies.EnemiesImpl;
import it.unibo.towerdefense.models.enemies.Enemy;
import it.unibo.towerdefense.views.enemies.EnemyRenderer;
import it.unibo.towerdefense.views.enemies.EnemyRendererImpl;
import it.unibo.towerdefense.views.graphics.GameRenderer;

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
        model = null;
        enemyRenderer = null;
        /*enemyRenderer = new EnemyRendererImpl(mc.getImageLoader());
        model = new EnemiesImpl((pos, speed) -> mc.getNextPosition(pos, speed), mc.getSpawnPosition());
        model.addDeathObserver(e -> {
            mc.addMoney(e.getValue());
            if (!model.isWaveActive()) {
                mc.advanceWave();
            }
        });*/
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

    /**
     * {@inhertDoc}
     */
    @Override
    public void render(GameRenderer renderer) {
        enemyRenderer.render(renderer, model.getEnemiesInfo());
    }
}
