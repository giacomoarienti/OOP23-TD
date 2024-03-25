package it.unibo.towerdefense.models.map;

import java.util.Optional;

/**
 * Interface that models a buildable cell.
 */
public interface BuildableCell extends Cell {

    /**
     *Cell build current state.
     * @return true if cell contains a defense, false if is empty.
     */
    boolean isBuilt();

    /**
     *Return the ID of defense present in the cell.
     * @return Optional of Object id of the defense, Optiona.empty if it is not built.
     */
    Optional<Long> getDefense(); // ???
}
