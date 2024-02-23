package it.unibo.towerdefense.controllers.enemies;

import java.util.Optional;

import it.unibo.towerdefense.models.defenses.EnemyChoiceStrategy;
import it.unibo.towerdefense.models.engine.Position;

public interface EnemyController {

    void update();

    void render();

    Optional<Position> hurtEnemiesAt(Position pos, Double radius, int amount, EnemyChoiceStrategy strategy);
}
