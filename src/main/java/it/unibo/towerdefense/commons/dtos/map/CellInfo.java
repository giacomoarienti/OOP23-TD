package it.unibo.towerdefense.commons.dtos.map;

import it.unibo.towerdefense.commons.engine.Position;

public interface CellInfo {

    Position getPosition();
    boolean isPathCell();
    boolean isBuildable();
    boolean isSelected();
    int getDirectionsSum();

}
