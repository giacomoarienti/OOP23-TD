package it.unibo.towerdefense.model.map;

/**
 * Interface that models a buildable cell, a Cell where you can build denfeses.
 */
public interface BuildableCell extends Cell {

    /**
     * Cell current state getter.
     * @return true if cell can contains a defense, false if not.
     */
    boolean isBuildable();

    /**
     * Cell current state setter.
     * @param isBuildable true if cell can contains a defense, false if not.
     */
    void setBuildable(boolean isBuildable);

}
