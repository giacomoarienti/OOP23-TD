package it.unibo.towerdefense.commons.dtos;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;

/**
 * DTO containing information about the dynamic part of the game which gets
 * rendered various times per second.
 */
public interface GameState {
    /**
     * Returns a Stream of DTOs for the enemies.
     *
     * @return a Stream of DTOs for the enemies
     */
    Stream<EnemyInfo> getEnemies();

    /**
     * Returns a Stream of DTOs for the map.
     *
     * @return a Stream of DTOs for the map
     */
    Stream<CellInfo> getMap();

    /**
     * Returns a Stream of DTOs for the defenses.
     *
     * @return a Stream of DTOs for the defenses
     */
    Stream<DefenseDescription> getDefenses();
}
