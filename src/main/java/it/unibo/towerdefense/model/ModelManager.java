package it.unibo.towerdefense.model;

import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;

public interface ModelManager {

    MapManager getMap();

    DefenseManager getDefenses();

    EnemiesManager getEnemies();

    GameManager getGame();

}