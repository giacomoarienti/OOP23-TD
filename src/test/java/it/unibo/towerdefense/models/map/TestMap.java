package it.unibo.towerdefense.models.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.controllers.map.MapControllerImpl;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.SizeImpl;

/**
 * Map tester with "diagonal" path.
 */
public class TestMap {
    private MapController map = new MapControllerImpl(new SizeImpl(20, 20), null, null);
    private Position spawn = map.getSpawnPosition();
    private LogicalPosition pos = new LogicalPosition(spawn.getX(), spawn.getY());
    private final static int ITERATION_MOVEMENT = 500;

    @Test
    void testGetNextPosition() {
        var opt = map.getNextPosition(pos, 10000);
        Assertions.assertEquals(new LogicalPosition(spawn.getX() + 3600 + 1800, spawn.getY() - 3600 - 1000), opt.get());

        while (opt.isPresent()) {
            pos = opt.get();
            opt = map.getNextPosition(pos, ITERATION_MOVEMENT);
        }
        Assertions.assertTrue(pos.getY() < ITERATION_MOVEMENT);
        Assertions.assertTrue(pos.getY() >= 0);
        Assertions.assertTrue(map.getEndPosition().equalsCell(pos));
    }
}
