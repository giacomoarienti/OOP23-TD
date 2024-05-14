package it.unibo.towerdefense.model.enemies;

import java.util.Optional;
import java.util.Set;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.Manager;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.PathVector;

public class EnemiesManagerImpl implements EnemiesManager {

    private Enemies enemies;

    EnemiesManagerImpl(){
        enemies = null;
    }

    @Override
    public void bind(ModelManager mm) {
        final MapManager map = mm.getMap();
        final GameManager game = mm.getGame();

        enemies = new EnemiesImpl(
                (pos, speed) -> convert(map.getNextPosition(LogicalPosition.copyOf(pos), speed)),
                convert(map.getSpawnPosition()).get());

        enemies.addDeathObserver(e -> {
            game.addMoney(e.getValue());
            if (!enemies.isWaveActive()) {
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
     * {@inheritDoc}.
     */
    @Override
    void update(){
        if(enemies == null){
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        enemies.update();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    void spawn(int wave){
        if(enemies == null){
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        enemies.spawn(wave);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    Set<? extends Enemy> getEnemies(){
        if(enemies == null){
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        return enemies.getEnemies();
    }
}
