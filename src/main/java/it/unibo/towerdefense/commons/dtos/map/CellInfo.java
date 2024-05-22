package it.unibo.towerdefense.commons.dtos.map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Data transfer Object for a Cell implementation.
 */
public interface CellInfo {

    /**
     * Position centre getter.
     * @return the logical position of centre of cell.
     */
    LogicalPosition getPosition();

    /**
     * Test if this is a PathCell or a BuildableCell.
     * @return true if this is a PathCell, false if it isn't.
     */
    boolean isPathCell();

    /**
     * Test if this is buildable or not.
     * @return true if this is buildable, false if it is a PathCell or contains a Obstacle.
     */
    boolean isBuildable();

    /**
     * Test if this cell is currently selected.
     * @return true if it is selected, false if it isn't.
     */
    boolean isSelected();

    /**
     * In and out Directions getter, should be unsupported if isPathCell is false.
     * @return a Pair <in, out> directions.
     */
    Pair<Direction, Direction> getDirections();

}
