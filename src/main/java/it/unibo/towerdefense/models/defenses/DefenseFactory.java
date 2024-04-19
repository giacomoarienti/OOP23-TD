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
     * @param reference the position to use for calculations.
     */
    Defense archerTower(String fileName, LogicalPosition reference);

    /**
     * @TODO : add other defenses
     */
}
