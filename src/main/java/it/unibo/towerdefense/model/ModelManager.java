package it.unibo.towerdefense.model;

import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;

/**
 * Interface that models the main ModelManager.
 * It's responsible for managing the model and initializing it.
 */
public interface ModelManager {

    /**
     * Get the MapManager.
     * @return the MapManager
     */
    MapManager getMap();

    /**
     * Get the DefenseManager.
     * @return the DefenseManager
     */
    DefenseManager getDefenses();

    /**
     * Get the EnemiesManager.
     * @return the EnemiesManager
     */
    EnemiesManager getEnemies();

    /**
     * Get the GameManager.
     * @return the GameManager
     */
    GameManager getGame();
}