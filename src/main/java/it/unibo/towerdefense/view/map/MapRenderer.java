package it.unibo.towerdefense.view.map;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * Interface that describe a Object to render the game map.
 */
public interface MapRenderer {

    /**
     * Render the map on Canvas of Renderer.
     * @param renderer the game renderer.
     * @param mapInfo stream with info for each cell of map.
     */
    void render(Renderer renderer, Stream<CellInfo> mapInfo);

}
