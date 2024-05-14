package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.controllers.Controller;

/**
 * The only interface to communicate with the enemies model.
 * Acts as mediator with other parts of the game and decouples
 * model and view.
 */
public interface EnemyController extends Controller {

    /**
     * Returns a list of all currently alive enemies' position and hp.
     * Index of enemies are guaranteed not to change in-between subsequent calls and
     * can be used to hurt enemies with hurtEnemies().
     *
     * @return the enemies' position and hp
     */
    List<Pair<LogicalPosition, Integer>> getEnemies();

    /**
     * Hurts the enemy which index in the list returned by the last call to
     * getEnemies corresponds to the key by the amount stored in the value.
     *
     * Will throw a RuntimeException if getEnemies has never been called before.
     *
     * @param indexes enemies to hurt
     */
    void hurtEnemies(Map<Integer, Integer> indexes);

    /**
     * Starts spawning the enemies for the wave given as argument.
     *
     * The amount and type of enemies are implementation-defined and are based on
     * the wave number.
     *
     * @param wave the ordinal number of the wave to spawn
     */
    void spawn(int wave);

}
