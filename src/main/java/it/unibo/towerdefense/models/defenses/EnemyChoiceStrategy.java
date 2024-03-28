package it.unibo.towerdefense.models.defenses;

import java.util.Set;

import it.unibo.towerdefense.models.enemies.Enemy;

public interface EnemyChoiceStrategy {
    Set<Enemy> chooseEnemies(Set<Enemy> set);

    void execute(Set<Enemy> availableTargets);
}
