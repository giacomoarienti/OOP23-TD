package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * Cell where enemyes can move, and where the player can't build defenses.
 */
public interface PathCell extends Cell {

    /**
     * Entry Direction  getter.
     * @return Direction from which enemies enter the cell
     */
    Direction getInDirection();

    /**
     * Exit Direction  getter.
     * @return Direction from which enemies exit the cell
     */
    Direction getOutDirection();

    /**
     * Returns the midpoint of in-direction's cell side.
     * @return logical position of the point.
     */
    LogicalPosition inSideMidpoint();
}
