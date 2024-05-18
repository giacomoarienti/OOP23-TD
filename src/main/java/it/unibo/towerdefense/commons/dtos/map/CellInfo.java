package it.unibo.towerdefense.commons.dtos.map;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;

public interface CellInfo {

    Position getPosition();
    boolean isPathCell();
    boolean isBuildable();
    int getDirectionsSum();

}
