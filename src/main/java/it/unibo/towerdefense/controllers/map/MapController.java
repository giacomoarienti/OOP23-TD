package it.unibo.towerdefense.controllers.map;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.commons.dtos.DefenseDescription;
import it.unibo.towerdefense.controllers.SerializableController;
import it.unibo.towerdefense.models.engine.Position;

/**
 *Interface that models controller of map.
 */
public interface MapController extends SerializableController {

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
     * Enemies destination point getter.
     * @return the centre of side of path-cell where enemies are directed.
     */
    LogicalPosition getEndPosition();

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
    void build (int optionNumber) throws IOException;

    /**
     * Returns the building options in the selected cell.
     * @return a list of DefenseDescripions
     */
    List<DefenseDescription> getBuildingOptions();

}
