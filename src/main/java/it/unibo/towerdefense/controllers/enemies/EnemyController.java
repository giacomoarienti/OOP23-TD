package it.unibo.towerdefense.controllers.enemies;

import java.util.List;
import java.util.Set;

import it.unibo.towerdefense.models.engine.Position;
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
     * @return the enemies
     */
    List<Pair<Position, Integer>> getEnemies();

    /**
     * Hurts the enemies (index, amount).
     * @param indexes enemies to hurt
     */
    void hurtEnemies(Set<Pair<Integer, Integer>> indexes);

}
