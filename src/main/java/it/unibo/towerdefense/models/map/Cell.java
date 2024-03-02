package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.models.engine.CollisionBox;

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

    CollisionBox getBox();

    boolean isBuildable();
}
