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
    private final SpawnerFactory factory;
    private final Enemies enemies;
    private final GameController gc;
    private Optional<Spawner> current = Optional.empty();

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
        this.factory = new SpawnerFactory(new SimpleEnemyFactory(startingPos, enemies));
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void update() {
        if(current.isPresent()){
            if(current.get().hasNext()){
                enemies.add(current.get().next());
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
    public void spawn(int wave) {
        if(current.isPresent()){
            throw new IllegalStateException("A wave is already being spawned.");
        }else{
            current = Optional.of(factory.build(supplier.apply(wave)));
        }
    }
}
