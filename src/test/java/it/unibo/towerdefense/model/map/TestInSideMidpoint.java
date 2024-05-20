package it.unibo.towerdefense.model.map;

import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.PositionImpl;

public class TestInSideMidpoint {
    private PathCell pc = new PathCellImpl(new PositionImpl(10, 10), MapDirection.E, MapDirection.E, 1000);

    @Test
    void testInSideMidPoint() {
        System.out.println(pc.inSideMidpoint());
    }
}
