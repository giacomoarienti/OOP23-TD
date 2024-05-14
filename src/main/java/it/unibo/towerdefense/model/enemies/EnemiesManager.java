package it.unibo.towerdefense.model.enemies;

import it.unibo.towerdefense.model.Manager;
import it.unibo.towerdefense.model.ModelManager;

public class EnemiesManager implements Manager {
    private Enemies enemies;

    EnemiesManager(){

    }

    @Override
    public void bind(ModelManager mm) {
        enemies = new EnemiesImpl(null, null);
    }
}
