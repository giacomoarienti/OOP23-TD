package it.unibo.towerdefense.model.map;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.commons.dtos.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.model.Manager;

/**
 *Interface that models controller of map.
 */
public interface MapManager extends JsonSerializable, Manager {

    /**
     * Enemies spawn point getter.
     * @return the centre of side of path-cell where enemies spawn.
     */
    PathVector getSpawnPosition();

    /**
     * Enemies destination point getter.
     * @return the centre of side of path-cell where enemies are directed.
     */
    LogicalPosition getEndPosition();

    /**
     * Returns the PathPosition where a enemy have to move next update.
     * @param pos current position
     * @param distanceToMove the distance an enemy travels each update
     * @return the PathPositon where the enemy will be located, if distanceToEnd is equal to 0 the enemy arrives at the end.
     */
    PathVector getNextPosition(LogicalPosition pos, int distanceToMove);

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
     * Returns the building options in the selected cell, and if they are purchasable.
     * @return a list of Pair: defense description on left, is purchasable on right.
     */
    Stream<Pair<DefenseDescription, Boolean>> getBuildingOptions();

}
