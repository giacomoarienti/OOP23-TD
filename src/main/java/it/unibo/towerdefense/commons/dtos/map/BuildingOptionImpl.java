package it.unibo.towerdefense.commons.dtos.map;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;

/**
 * Class that implement BuildingOption for a defense building or upgrading.
 */
public class BuildingOptionImpl implements BuildingOption {

    private final boolean isAvailable;
    private final String text;
    private final String cost;
    private final String description;

    /**
     * Constructor from a defense description and if option is available.
     * @param dd
     * @param isAvailable
     */
    public BuildingOptionImpl(final DefenseDescription dd, final boolean isAvailable) {
        text = dd.getName();
        cost = Integer.toString(dd.getCost());
        this.isAvailable = isAvailable;
        this.description = dd.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCost() {
        return cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

}
