package it.unibo.towerdefense.commons.engine;

/**
 * A class which extends Position adding cell-based logic.
 * A cell is SCALING_FACTOR positions long.
 */
public class LogicalPosition extends PositionImpl {

    /**
     * 1/scalingFactor of a cell is the smallest variation registered
     * a cell is scalingFactor positions long.
     */
    public static final int SCALING_FACTOR = 3600;

    /**
     * Constructor for the class.
     *
     * @param x the x
     * @param y the y
     */
    public LogicalPosition(final int x, final int y) {
        super(x, y);
    }

    /**
     * Getter for the x of the corresponding cell.
     *
     * @return the x of the corresponding cell.
     */
    public int getCellX() {
        return getCellCoord(getX());
    }

    /**
     * Getter for the y of the corresponding cell.
     *
     * @return the y of the corresponding cell.
     */
    public int getCellY() {
        return getCellCoord(getY());
    }

    /**
     * Test if this and an other position are in the same Cell.
     *
     * @param pos position to compare to this.
     * @return true if they are in the same Cell, false if not
     */
    public boolean equalsCell(final LogicalPosition pos) {
        return this.getCellX() == pos.getCellX() && this.getCellY() == pos.getCellY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition copy() {
        return new LogicalPosition(this.getX(), this.getY());
    }

    /**
     * Compact setter.
     *
     * @param x the new x
     * @param y the new y
     */
    public void set(final int x, final int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Returns the scaling factor used in the logical position calculations.
     *
     * @return the scaling factor
     */
    public int getScalingFactor() {
        return SCALING_FACTOR;
    }

    /**
     * Returns the relative x of position in map.
     * @return x as a double in cells unit of measure.
     */
    public double getRelativeX() {
        return getX() / (double) SCALING_FACTOR;
    }

    /**
     * Returns the relative y of position in map.
     * @return y as a double in cells unit of measure.
     */
    public double getRelativeY() {
        return getY() / (double) SCALING_FACTOR;
    }

    /**
     * Overload for fromJson method.
     * @param jsonData JSON string.
     * @return the Logical Position.
     */
    public static LogicalPosition fromJson(final String jsonData) {
        final Position result = Position.fromJson(jsonData);
        return new LogicalPosition(result.getX(), result.getY());
    }

    private static int getCellCoord(final int posCoord) {
        if (posCoord < 0) {
            return (posCoord + 1) / SCALING_FACTOR - 1;
        }
        return posCoord / SCALING_FACTOR;
    }

    /**
     * Returns an immutable LogicalPosition with the same x and y as the argument.
     *
     * @param pos the position to copy
     * @return an immutable LogicalPosition with the same x and y as the argument
     */
    public static LogicalPosition copyOf(final LogicalPosition pos) {
        return new LogicalPosition(pos.getX(), pos.getY()) {
            /**
             * {@inheritDoc}
             */
            @Override
            public void setX(final int x) {
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void setY(final int y) {
                throw new UnsupportedOperationException("Tried to modify an immutable position.");
            }
        };
    }
}
