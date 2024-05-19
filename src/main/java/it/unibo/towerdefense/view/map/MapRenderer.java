package it.unibo.towerdefense.view.map;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.view.graphics.Renderer;

public interface MapRenderer{
    void renderPath(Renderer gr, Stream<CellInfo> map);
}
