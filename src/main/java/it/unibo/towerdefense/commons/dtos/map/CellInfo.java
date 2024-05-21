package it.unibo.towerdefense.commons.dtos.map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

public interface CellInfo {

    LogicalPosition getPosition();
    boolean isPathCell();
    boolean isBuildable();
    boolean isSelected();
    Pair<Direction, Direction> getDirections();

}
