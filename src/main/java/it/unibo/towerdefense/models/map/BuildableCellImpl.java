package it.unibo.towerdefense.models.map;

import it.unibo.towerdefense.models.engine.Position;

/**
 * Class that implements BuildableCell.
 */
public class BuildableCellImpl extends CellAbs implements BuildableCell {

    private boolean isBuildable;

    /**
     * Constructor from coordinates and 2 opposite vertex positions.
     * @param coords coordinates x y to identify the cell in the map.
     * @param isBuildable true if cell can contains a defense, false if not.
     */
    public BuildableCellImpl(final Position coords, final boolean isBuildable) {
        super(coords);
        this.isBuildable = isBuildable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBuildable() {
        return isBuildable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuildable(boolean isBuildable) {
        this.isBuildable = isBuildable;
    }

}
