package it.unibo.towerdefense.commons.dtos;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;

public interface GameState {
    Stream<EnemyInfo> getEnemies();
}
