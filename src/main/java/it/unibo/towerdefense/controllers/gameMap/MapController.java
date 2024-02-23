package it.unibo.towerdefense.controllers.gameMap;

import it.unibo.towerdefense.models.engine.Position;

public interface MapController {
    void update();
    Position getNext(Position from);
    void select(Position cell);
    Position getSelected();
    boolean isEdifiable(Position cell);
}
