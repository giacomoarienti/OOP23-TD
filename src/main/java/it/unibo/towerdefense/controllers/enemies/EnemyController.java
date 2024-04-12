package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

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
    List<Pair<Pair<Integer, Integer>, Integer>> getEnemies();

    /**
     * Hurts the enemies (index, amount).
     *
     * @param indexes enemies to hurt
     * @throws IllegalStateException if getEnemies has never been called.
     */
    void hurtEnemies(Set<Pair<Integer, Integer>> indexes);

}
