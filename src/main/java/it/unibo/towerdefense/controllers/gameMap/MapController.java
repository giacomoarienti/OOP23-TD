package it.unibo.towerdefense.controllers.gameMap;

import java.util.Optional;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.map.BuildableCell;
import it.unibo.towerdefense.models.map.PathCell;

public interface MapController {

    /**
     *
     */
    void update();

    /**
     *Return the next path cell from current.
     * @param current the current cell
     * @return the next cell
     */
    Optional<PathCell> getNext(PathCell current);

    /**
     *Select the cell which contains the position clicked by the user.
     * @param position the position clicked by user
     */
    void select(Position position);
    Optional<BuildableCell> getSelected();

}
