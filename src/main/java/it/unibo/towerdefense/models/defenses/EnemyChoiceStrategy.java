package it.unibo.towerdefense.models.defenses;

import java.util.Set;

import it.unibo.towerdefense.models.enemies.Enemy;

public interface EnemyChoiceStrategy {
    Enemy chooseEnemy(Set<Enemy> set);
}
