package it.unibo.towerdefense.commons.dtos.map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.engine.Direction;

public interface CellInfo {
    enum Type{
        BUILDABLE, PATH;
    }

    Type getType();
    boolean isBuildable();
    Pair<Direction, Direction> getDirections();

}
