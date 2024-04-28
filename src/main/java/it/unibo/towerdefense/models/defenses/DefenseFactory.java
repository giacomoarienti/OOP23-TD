package it.unibo.towerdefense.models.defenses;

import java.io.IOException;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * A factory for building the defenses tha will be available in game.
 */

public interface DefenseFactory {
    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json save file.
     * @param fileName the json file with the attribute values for this defense.
     * @throws IOexception if file related errors occur.
     */
    Defense archerTowerFromSaveFile(String fileName) throws IOException;

    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * If the defense was not recovered from a previous save,then this method will be used instead of ArcherTower.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json file.
     * @param fileName the json file with the attribute values for this defense.
     * @param buildPosition the position of the tower.
     * @throws IOexception if file related errors occur.
     */
    Defense newArcherTowerFrom(String fileName, LogicalPosition buildPosition) throws IOException;
}
