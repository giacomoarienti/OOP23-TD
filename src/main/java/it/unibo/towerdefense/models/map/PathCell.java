package it.unibo.towerdefense.models.map;

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
     * Return the distandce from end of path.
     * @return number of cell between this and last (last cell gives 0, next to last 1, ...)
     */
    /*int distanceToEnd();*/
}
