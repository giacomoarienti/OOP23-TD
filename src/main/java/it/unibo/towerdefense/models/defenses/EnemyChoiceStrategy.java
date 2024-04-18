package it.unibo.towerdefense.models.defenses;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.enemies.Enemy;


/**
 * The strategy responsible for selecting targets and calculating the amount of damage to deal.
 */
public interface EnemyChoiceStrategy {
    /**
     * @param set the pool of enemies that can be selected as targets if they fit the strategy.
     * @param towerPosition the position from wich the distances are calculated.
     * @return the enemies that are eligible to be damaged.
     */
    List<Enemy> chooseEnemies(List<Enemy> set, LogicalPosition towerPosition);

    /**
     * Executes the strategy.
     * @param availableTargets the possible targets to attack,must be filtered by chooseEnemies method.
     * @param baseDamage the base damage stat of the tower executing the strategy.
     * @return a map with the key indicating index of entity to damage and the value indicating the damage to inflict.
     */
    Map<Integer, Integer> execute(List<Pair<LogicalPosition, Integer>> availableTargets, int baseDamage);
}
