package it.unibo.towerdefense.model;

import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.enemies.Enemies;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;

public interface ModelManager {

    MapManager getMap();

    DefenseManager getDefenses();

    Enemies getEnemies();

    GameManager getGame();

}