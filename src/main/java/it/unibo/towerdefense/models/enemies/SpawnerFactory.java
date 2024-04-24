package it.unibo.towerdefense.models.enemies;

import java.util.Iterator;

public class SpawnerFactory {

    private final EnemyFactory factory;

    SpawnerFactory(EnemyFactory factory){
        this.factory = factory;
    };

    public Spawner build(Wave w) {
        Iterator<EnemyType> it;
        if(!(it = w.iterator()).hasNext()){
            throw new IllegalArgumentException("Tried to init an empty wave.");
        }else{
            return new Spawner() {
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }
                @Override
                public Enemy next() {
                    return factory.build(it.next());
                }
            };
        }
    }
}
