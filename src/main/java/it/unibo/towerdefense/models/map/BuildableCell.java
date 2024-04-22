package it.unibo.towerdefense.models.map;

/**
 * Interface that models a buildable cell.
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
