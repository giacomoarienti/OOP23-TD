package it.unibo.towerdefense.controllers.mediator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.controllers.enemies.EnemyController;
import it.unibo.towerdefense.controllers.game.GameController;

/**
 * Interface that models a mediator between controllers.
 */
public interface ControllerMediator extends Controller {

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
     * Function wrapper for the {@link MapController#getEndPosition()}.
     * @return the final position of the map
     */
    LogicalPosition getEndPosition();

    /**
     * Function wrapper for the {@link EnemyController#getEnemies()}.
     * @return a list of position and health of the enemies
     */
    List<Pair<LogicalPosition, Integer>> getEnemies();

    /**
     * Function wrapper for the {@link MapController#getNextPosition()}.
     * @param pos current position
     * @param distanceToMove the distance an enemy travels each update
     * @return the position where the enemy will be located, empty if it reached the end
     */
    Optional<LogicalPosition> getNextPosition(LogicalPosition pos, int distanceToMove);

    /**
     * Function wrapper for the {@link MapController#getSpawnPosition()}.
     * Enemies spawn point getter.
     * @return the centre of side of path-cell where enemies spawn.
     */
    LogicalPosition getSpawnPosition();

    /**
     * Function wrapper for the {@link MapController#select()}.
     * @param amount quantity of money to be increased
     */
    void addMoney(int amount);

    /**
     * Function wrapper for the {@link GameController#advanceWave()}.
     */
    void advanceWave();

    /**
     * Function wrapper for the {@link EnemyController#hurtEnemies()}
     * @param enemies a map of enemies and the damage they take
     */
    void hurtEnemies(Map<Integer,Integer> enemies);
}
