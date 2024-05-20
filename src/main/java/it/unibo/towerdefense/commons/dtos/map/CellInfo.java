package it.unibo.towerdefense.commons.dtos.map;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

public interface CellInfo {

    LogicalPosition getPosition();
    boolean isPathCell();
    boolean isBuildable();
    boolean isSelected();
    int getDirectionsSum();

}
