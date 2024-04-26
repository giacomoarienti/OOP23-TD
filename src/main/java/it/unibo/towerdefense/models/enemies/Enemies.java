package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Set;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

public interface Enemies {
    void update();
    void spawn(final int wave);
    Set<Enemy> getEnemies();
    List<EnemyInfo> getEnemiesInfo();
}
