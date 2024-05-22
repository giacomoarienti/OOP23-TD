package it.unibo.towerdefense.commons.dtos.map;

/**
 * Data transfer Object for building menu.
 */
public interface BuildingOption {

    /**
     * Option name getter.
     * @return name of this option.
     */
    String getText();

    /**
     * Money value getter.
     * @return text of value of this option.
     */
    String getCost();

    /**
     * Test if this option is available.
     * @return tru if it is available, false if not.
     */
    boolean isAvailable();
}
