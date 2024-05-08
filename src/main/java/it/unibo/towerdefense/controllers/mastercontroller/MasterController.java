package it.unibo.towerdefense.controllers.mastercontroller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.Controller;

/**
 * Interface that models the main controller.
 * It's job is mediate between the different controllers.
 */
public interface MasterController extends Controller {

    /**
     * Sets gameState to PLAYING.
     */
    void resume();

    /**
     * Sets gameState to PAUSE.
     */
    void pause();

    /**
     * Saves the game.
     */
    void save();

    /**
     * Returns if the game loop should run.
     * @return true if game is running
     */
    boolean isRunning();

    /**
     * Function wrapper for the {@link it.unibo.towerdefense.controllers.map.MapController#getEndPosition()}.
     * @return the final position of the map
     */
    LogicalPosition getEndPosition();


    /**
     * Function wrapper for the {@link it.unibo.towerdefense.controllers.enemies.EnemyController#getEnemies()}.
     * @return a list of position and health of the enemies
     */
    List<Pair<LogicalPosition, Integer>> getEnemies();

    /**
     * Function wrapper for the {@link it.unibo.towerdefense.controllers.enemies.EnemyController#hurtEnemies()}
     * @param enemies a map of enemies and the damage they take
     */
    void hurtEnemies(Map<Integer,Integer> enemies);
}
