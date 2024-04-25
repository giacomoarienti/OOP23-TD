package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * A factory for building the defenses tha will be available in game.
 */

public interface DefenseFactory {
    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json file.
     * @param fileName the json file with the attribute values for this defense.
     */
    Defense archerTower(String fileName);

    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * If the defense was not recovered from a previous save,then this method will be used instead of ArcherTower.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json file.
     * @param fileName the json file with the attribute values for this defense.
     * @param reference the position of the tower.
     */
    Defense archerTowerFromPosition(String fileName, LogicalPosition reference);
}
