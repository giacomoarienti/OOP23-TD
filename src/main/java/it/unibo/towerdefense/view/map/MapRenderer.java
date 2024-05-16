package it.unibo.towerdefense.view.map;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.view.graphics.GameRenderer;

public interface MapRenderer{
    void renderPath(GameRenderer gr, Stream<CellInfo> map);
}
