package it.unibo.towerdefense.commons.dtos.map;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;

public class BuildingOptionImpl implements BuildingOption{

    private final boolean isPurchasable;
    private final String text;
    private final String cost;

    public BuildingOptionImpl(DefenseDescription dd, boolean isPurchasable) {
        text = dd.getName();
        cost = Integer.toString(dd.getCost());
        this.isPurchasable = isPurchasable;
    }

    @Override
    public boolean isPurchasable() {
        return isPurchasable;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getCost() {
        return cost;
    }
}
