package it.unibo.towerdefense.model;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.saving.Saving;

public interface ModelManager {

    void init(String playerName, Size mapSize);

    void init(Saving saving);

    MapManager getMap();

    DefenseManager getDefenses();

    EnemiesManager getEnemies();

    GameManager getGame();

}