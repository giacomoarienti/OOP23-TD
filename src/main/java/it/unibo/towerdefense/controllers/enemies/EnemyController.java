package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * Interface which defines the enemies' controller public methods.
 */
public interface EnemyController {

    /**
     * Updates the enemies.
     */
    void update();

    /**
     * Renders the enemies.
     */
    void render();

    /**
     * Gets the enemies.
     * Index of enemies guaranteed not to change in-between subsequent calls.
     *
     * @return the enemies' position and hp
     */
    List<Pair<LogicalPosition, Integer>> getEnemies();

    /**
     * Hurts the enemies (index, amount).
     *
     * @param indexes enemies to hurt
     * @throws IllegalStateException if getEnemies has never been called.
     */
    void hurtEnemies(Map<Integer, Integer> indexes);

    /**
     * Spawns the enemies corresponding to the given wave.
     *
     * The amount and type of enemies are arbitrarily decided based on the wave number.
     * @param wave
     */
    void spawn(int wave);

}
