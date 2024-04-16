package it.unibo.towerdefense.controllers.map;

import java.util.Optional;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.map.BuildableCell;
import it.unibo.towerdefense.models.map.PathCell;

/**
 *Interface that models controller of map.
 */
public interface MapController {

    /**
     *
     */
    void update();

    /**
     * Enemies spawn Cell getter.
     * @return the first cell of path.
     */
    PathCell getSpawnCell();

    /**
     *Return the next path cell from current.
     * @param current the current cell
     * @return Optional of the next cell, Optional.empty if @current is the last of path
     */
    Optional<PathCell> getNext(PathCell current);

    /**
     *Select the cell which contains the position clicked by the user, if cell is already selected it is deselected.
     * @param position the position clicked by user
     */
    void select(Position position);

    /**
     *Return an optional of the current selected cell.
     * @return Optional of the cell if a cell is currently selected and is a BuildableCell, else Optional.empty.
     */
    Optional<BuildableCell> getSelected();

}
