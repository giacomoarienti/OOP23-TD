package it.unibo.towerdefense.commons.dtos.map;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

public class BuildingOption extends DefenseDescription{

    private final boolean isPurchasable;

    public BuildingOption(DefenseDescription dd, boolean isPurchasable) {
        super(dd.getDescription(), dd.getName(), dd.getCost(), dd.getSellValue(), dd.getLevel(), dd.getType(), dd.getPosition());
        this.isPurchasable = isPurchasable;
    }

    public boolean isPurchasable() {
        return isPurchasable;
    }

    @Override
    public LogicalPosition getPosition() {
        throw new UnsupportedOperationException("Can't use this method for this class");
    }
}
