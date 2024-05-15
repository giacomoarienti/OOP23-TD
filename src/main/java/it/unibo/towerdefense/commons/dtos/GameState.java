package it.unibo.towerdefense.commons.dtos;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;

public interface GameState {
    Stream<EnemyInfo> getEnemies();
    Stream<CellInfo> getMap();
}