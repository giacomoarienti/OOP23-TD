package it.unibo.towerdefense.model;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.saving.Saving;

/**
 * Interface that models the main ModelManager.
 * It's responsible for managing the model and initializing it.
 */
public interface ModelManager {

    /**
     * Initialize the model.
     * @param playerName the player name
     * @param mapSize the size of the map
     */
    void init(String playerName, Size mapSize);

    /**
     * Initialize the model from a saving object.
     * @param saving the saving object
     */
    void init(Saving saving);

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