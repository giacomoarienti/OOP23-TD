package it.unibo.towerdefense.models.map;

/**
 *Interface describing a 2D oriented sequnce of adjacent coordinates.
 */
public interface Path {

    /**
     * Return the next point coordinates of pathfrom current.
     * @param coords the current cell
     * @return the coordinates of next poin in the path, null if current has no next.
     */
    Coords getNext(Coords coords);

    /**
     * Return the previous point coordinates of pathfrom current.
     * @param coords the current cell
     * @return the coordinates of previous point in the path, null if current has no previous.
     */
    Coords getPrevious(Coords coords);
}
