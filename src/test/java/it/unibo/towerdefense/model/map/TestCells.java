package it.unibo.towerdefense.model.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.PositionImpl;

public class TestCells {

    @Test
    void testGetCentre() {
        LogicalPosition lp = new LogicalPosition(1800, 1800);
        Assertions.assertEquals(lp, new BuildableCellImpl(new PositionImpl(lp.getCellX(), lp.getCellY()), false).getCenter());
    }

    @Test
    void testInSideMidPoint() {
        for (MapDirection d : MapDirection.values()) {
            PathCell pc = new PathCellImpl(new PositionImpl(10, 10), d, MapDirection.E, 1000);
            Assertions.assertTrue(pc.contains(pc.inSideMidpoint()));
        }
    }

}
