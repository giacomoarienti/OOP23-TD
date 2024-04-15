package it.unibo.towerdefense.models.defenses;

import java.util.Set;

import it.unibo.towerdefense.models.enemies.Enemy;
import it.unibo.towerdefense.models.engine.Position;

/**
 * The strategy responsible for selecting targets and calculating the amount of damage to deal.
 */
public interface EnemyChoiceStrategy {
    /**
     * @param set the pool of enemies that can be selected as targets if they fit the strategy.
     * @param towerPosition the position from wich the distances are calculated.
     * @return the enemies that are eligible to be damaged.
     */
    Set<Enemy> chooseEnemies(Set<Enemy> set, Position towerPosition);

    /**
     * @param availableTargets the enemies obtained by the method choseEnemies.
     * @param baseDamage the base damage stat of the tower executing the strategy.
     * @return true if there was at least one enemy that was hit.
     */
    boolean execute(Set<Enemy> availableTargets, int baseDamage);
}
