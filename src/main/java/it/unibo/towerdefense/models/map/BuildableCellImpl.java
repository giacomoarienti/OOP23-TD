package it.unibo.towerdefense.models.map;

import org.json.JSONObject;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;

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
    public void setBuildable(final boolean isBuildable) {
        this.isBuildable = isBuildable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put("pos", ((Position) this).toJSON())
            .put("isBuildable", isBuildable)
            .toString();
    }

    /**
     * Returns the BuildableCell object from JSON string.
     * @param jsonData the JSON representation
     * @return the BuildableCell object
     */
    public static BuildableCell fromJson(final String jsonData) {
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new BuildableCellImpl(PositionImpl.fromJson(jsonObject.getString("pos")), jsonObject.getBoolean("isBuildable"));
    }
}
