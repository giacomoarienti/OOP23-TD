package it.unibo.towerdefense.models.map;

import java.util.NoSuchElementException;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.Size;

public interface GameMap {

    /**
     * Map size getter.
     * @return Size of map in cell.
     */
    Size getSize();

    /**
     * Return the Cell at given position.
     * @param position
     * @return the Cell containing position
     */
    Cell getCellAt(Position position);

    /**
     * Enemies spawn cell getter.
     * @return the first cell of path.
     */
    PathCell getSpawnCell();

    /**
     * Return the next path cell from current.
     * @param current the current cell
     * @return the next cell in the path
     * @throws NoSuchElementException if current is a finish cell of path
     */
    PathCell getNext(PathCell current) throws NoSuchElementException;
}
