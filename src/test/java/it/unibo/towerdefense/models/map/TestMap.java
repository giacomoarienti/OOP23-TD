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

    @Test
    void testGetNextPosition() {
        var op = map.getNextPosition(pos, 10000);
        Assertions.assertEquals(new LogicalPosition(spawn.getX() + 3600 + 1800, spawn.getY() - 3600 - 1000), op.get());

        while (op.isPresent()) {
            pos = op.get();
            op = map.getNextPosition(pos, 100);
        }
        Assertions.assertTrue(100 > pos.getY());
        Assertions.assertTrue(0 <= pos.getY());
    }
}
