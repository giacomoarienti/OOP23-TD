package it.unibo.towerdefense.model.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.PositionImpl;

/**
 * Class to test Cell methods.
 */
class TestCell {

    private static final int HALF_FACTOR = LogicalPosition.SCALING_FACTOR / 2;

    @Test
    void testGetCentre() {
        final LogicalPosition lp = new LogicalPosition(HALF_FACTOR, HALF_FACTOR);
        Assertions.assertEquals(lp, new BuildableCellImpl(new PositionImpl(lp.getCellX(), lp.getCellY()), false).getCenter());
    }

    @Test
    void testInSideMidPoint() {
        for (final MapDirection d : MapDirection.values()) {
            final PathCell pc = new PathCellImpl(new PositionImpl(3, 3), d, MapDirection.E, 1000);
            Assertions.assertTrue(pc.contains(pc.inSideMidpoint()));
        }
    }

}
