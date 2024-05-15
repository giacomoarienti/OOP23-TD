package it.unibo.towerdefense.view.map;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.view.graphics.GameRenderer;

public interface MapRenderer{
    void renderPath(GameRenderer gameRenderer, Stream<LogicalPosition> path);
}
