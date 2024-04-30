package it.unibo.towerdefense.controllers.map;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.engine.Position;

/**
 *Interface that models controller of map.
 */
public interface MapController {

    /**
     *
     */
    void update();

    /**
     * Enemies spawn point getter.
     * @return the centre of side of path-cell where enemies spawn.
     */
    LogicalPosition getSpawnPosition();

    /**
     * Returns the position where a enemy have to move next update.
     * @param pos current position
     * @param distanceToMove the distance an enemy travels each update
     * @return the position where the enemy will be located, empty if it reached the end
     */
    Optional<LogicalPosition> getNextPosition(LogicalPosition pos, int distanceToMove);

    /**
     *Select the cell which contains the position clicked by the user, if cell is already selected it is deselected.
     * @param position the position clicked by user
     */
    void select(Position position);

    /**
     *Return an optional of the current selected cell.
     * @return Optional of the cell if a cell is currently selected and is a BuildableCell, else Optional.empty.
     */
    Optional<Position> getSelected();

    /**
     * Build a tower in the selected Cell.
     * @param optionNumber the index of tower to build in the option list.
     */
    void build(int optionNumber);

    /**
     * Returns the building options in the selected cell.
     * @return a list of pairs <defense name, cost>
     */
    List<Pair<String, Integer>> getBuildingOptions();

    /**
     * Returns a String in JSON with all map information needed.
     * @return JSON String of game map
     */
    String getMapJSON();

}
