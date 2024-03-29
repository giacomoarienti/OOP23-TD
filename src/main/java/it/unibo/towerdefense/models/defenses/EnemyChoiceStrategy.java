package it.unibo.towerdefense.models.defenses;

import java.util.Set;

import it.unibo.towerdefense.models.enemies.Enemy;

/**
 * The strategy responsible for selecting targets and calculating the amount of damage to deal.
 */
public interface EnemyChoiceStrategy {
    /**
     * @param set the pool of enemies that can be selected as targets if they fit the strategy
     * @return the enemies that are eligible to be damaged
     */
    Set<Enemy> chooseEnemies(Set<Enemy> set);

    /**
     * @param availableTargets the enemies obtained by the method choseEnemies
     */
    void execute(Set<Enemy> availableTargets);
}
