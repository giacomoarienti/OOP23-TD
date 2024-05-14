package it.unibo.towerdefense.model.map;

import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;

/**
 * The entity that contain game map informations, with some utility methods to use in 2D space.
 */
public interface GameMap extends JsonSerializable {

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
     * End of path getter.
     * @return the cell were enemies want to go.
     */
    PathCell getEndCell();

}
