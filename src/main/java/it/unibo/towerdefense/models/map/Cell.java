package it.unibo.towerdefense.models.map;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.models.engine.Position;

public interface Cell {

    /**
     *I getter.
     * @return the orizontal index of the cell in the map.
     */
    int getI();

    /**
     *J getter.
     * @return the vertical index of the cell in the map.
     */
    int getJ();

    /**
     *Return the position of 2 opposite vertex of the rectangular cell.
     * @return Pair<up-left, down-right>
     */
    Pair<Position, Position> getOppositeVertex();

    /**
     *Cell size getter.
     * @return Size of square Cell.
     */
    int getSize();
}
