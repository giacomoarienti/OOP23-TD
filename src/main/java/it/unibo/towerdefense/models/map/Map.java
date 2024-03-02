package it.unibo.towerdefense.models.map;

import java.util.Set;

import it.unibo.towerdefense.models.engine.Size;

public interface Map {

    /**
     * Map size getter.
     * @return Size of map in cell.
     */
    Size getSize();

    /**
     * Path Graph getter.
     * @return the graph (as set of sets) of PathCell
     */
    Set<Set<PathCell>> getPath();
}
