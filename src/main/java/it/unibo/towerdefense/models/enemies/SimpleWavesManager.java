package it.unibo.towerdefense.models.enemies;

import java.util.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.game.GameController;

/**
 * @inheritDoc .
 */
public class SimpleWavesManager implements WavesManager {

    private final WaveSupplier supplier = new WaveSupplier() {
        @Override
        public Wave apply(Integer t) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'apply'");
        }
    };
    private final EnemySpawner spawner;
    private final Enemies enemies;
    private final GameController gc;
    private Optional<Wave> current = Optional.empty();

    /**
     * The constructor for the class.
     *
     * @param enemies
     * @param gc
     * @param startingPos
     */
    public SimpleWavesManager(Enemies enemies, GameController gc, LogicalPosition startingPos){
        this.enemies = enemies;
        this.gc = gc;
        this.spawner = new SimpleEnemySpawner(startingPos, enemies);
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
