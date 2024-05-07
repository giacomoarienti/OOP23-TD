package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;

/**
 * Simple record which implements richenemytype for testing.
 */
record TestEnemyType(EnemyLevel level, EnemyArchetype type, int getMaxHP, int getSpeed, int getValue) implements RichEnemyType{};