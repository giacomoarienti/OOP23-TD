package it.unibo.towerdefense.views.map;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

import it.unibo.towerdefense.commons.graphics.GameRenderer;

public interface MapRenderer{
    void renderPath(GameRenderer gameRenderer, Stream<LogicalPosition> path);
}
