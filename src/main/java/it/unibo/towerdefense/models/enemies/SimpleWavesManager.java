package it.unibo.towerdefense.models.enemies;

import java.util.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.game.GameController;

/**
 * @inheritDoc .
 */
public class SimpleWavesManager implements WavesManager {

    private final EnemyCollection enemies;
    private final GameController gc;
    private final EnemySpawner spawner;
    private final WaveSupplier supplier;
    private Optional<Wave> current = Optional.empty();

    /**
     * The constructor for the class.
     *
     * @param enemies
     * @param gc
     * @param startingPos
     */
    public SimpleWavesManager(EnemyCollection enemies, GameController gc, LogicalPosition startingPos, WavePolicySupplier wp){
        this.enemies = enemies;
        this.gc = gc;
        this.spawner = new SimpleEnemySpawner(startingPos, enemies);
        this.supplier = new PredicateBasedRandomWaveGenerator(wp);
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void update() {
        if(current.isPresent()){
            if(current.get().hasNext()){
                current.get().next().ifPresent(et -> spawner.spawn(et));
            }else{
                current = Optional.empty();
            }
        }else{
            if(enemies.areDead()){
                gc.advanceWave();
            }
        }
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void spawn(int waveNumber) {
        if(current.isPresent()){
            throw new IllegalStateException("A wave is already being spawned.");
        }else{
            current = Optional.of(supplier.apply(waveNumber));
        }
    }
}
