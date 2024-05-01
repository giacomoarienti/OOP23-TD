package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;

public class EnemiesImpl implements Enemies {

    private final String waveConfigFile = "it/unibo/towerdefense/models/enemies/waves.config";
    private final String typesConfigFile = "it/unibo/towerdefense/models/enemies/types.json";
    private final EnemyCollection enemies;
    private final EnemySpawner spawner;
    private final WaveSupplier supplier;
    private final GameController gc;
    private Optional<Wave> current = Optional.empty();

    public EnemiesImpl(final MapController map, final GameController gc){
        this.enemies = new EnemyCollectionImpl(map);
        this.spawner = new SimpleEnemySpawner(map.getSpawnPosition(), enemies);
        this.supplier = new PredicateBasedRandomWaveGenerator(new WavePolicySupplierImpl(waveConfigFile), new ConfigurableEnemyCatalogue(typesConfigFile));
        this.gc = gc;
    }

    @Override
    public void update() {
        enemies.move();
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

    @Override
    public Set<Enemy> getEnemies() {
        return enemies.getEnemies();
    }

    @Override
    public List<EnemyInfo> getEnemiesInfo() {
        return enemies.getEnemiesInfo();
    }

    @Override
    public void spawn(int wave) {
        if(current.isPresent()){
            throw new IllegalStateException("A wave is already being spawned.");
        }else{
            current = Optional.of(supplier.apply(wave));
        }
    }
}
