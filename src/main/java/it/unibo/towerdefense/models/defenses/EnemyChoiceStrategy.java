package it.unibo.towerdefense.models.defenses;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
/**
 * The strategy responsible for selecting targets and calculating the amount of damage to deal.
 */
public interface EnemyChoiceStrategy {
    /**
     * Executes the strategy.
     * @param availableTargets the possible targets to attack,must be filtered by chooseEnemies method.
     * @param baseDamage the base damage stat of the tower executing the strategy.
     * @return a map with the key indicating index of entity to damage and the value indicating the damage to inflict.
     */
    Map<Integer, Integer> execute(List<Pair<LogicalPosition, Integer>> availableTargets, int baseDamage);
}
