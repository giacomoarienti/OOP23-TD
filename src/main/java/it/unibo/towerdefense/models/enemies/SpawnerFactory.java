package it.unibo.towerdefense.models.enemies;

import java.util.Iterator;

import java.util.Optional;

public class SpawnerFactory {

    private final EnemyFactory factory;

    SpawnerFactory(EnemyFactory factory){
        this.factory = factory;
    };

    public Spawner build(Wave w) {
        Iterator<Optional<EnemyType>> it;
        if(!(it = w.iterator()).hasNext()){
            throw new IllegalArgumentException("Tried to init an empty wave.");
        }else{
            return new Spawner() {
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }
                @Override
                public Optional<Enemy> next() {
                    return it.next().map(et -> factory.build(et));
                }
            };
        }
    }
}
