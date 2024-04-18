package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.controllers.defenses.DefenseType;

/**
 * Interface that models a buildable cell.
 */
public interface BuildableCell extends Cell {

    /**
     * Cell build current state.
     * @return true if cell contains a defense, false if is empty.
     */
    boolean isBuilt();

    /**
     * Return the defense present in the cell.
     * @return the defense present in the cell, NOTOWER if is not present.
     */
    DefenseType getDefense();

    /**
     * Build the defense in this cell.
     * @param defense type of defense to build, NOTOWER to sell the tower.
     */
    void build(DefenseType defense);
}
