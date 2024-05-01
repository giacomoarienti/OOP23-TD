package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Set;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;

public class EnemiesImpl implements Enemies {

    private final String waveConfigFile = "it/unibo/towerdefense/models/enemies/waves.config";
    private final String typesConfigFile = "it/unibo/towerdefense/models/enemies/types.config";
    private final EnemyCollection collection;
    private final WavesManager waves;

    public EnemiesImpl(final MapController map, final GameController gc){
        this.collection = new EnemyCollectionImpl(map);
        this.waves = new SimpleWavesManager(collection, gc, map.getSpawnPosition(), new WavePolicySupplierImpl(waveConfigFile), new ConfigurableEnemyCatalogue(typesConfigFile));
    }

    @Override
    public void update() {
        collection.move();
        waves.update();
    }

    @Override
    public Set<Enemy> getEnemies() {
        return collection.getEnemies();
    }

    @Override
    public List<EnemyInfo> getEnemiesInfo() {
        return collection.getEnemiesInfo();
    }

    @Override
    public void spawn(int wave) {
        waves.spawn(wave);
    }
}
