package it.unibo.towerdefense.models.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.controllers.map.MapControllerImpl;

/**
 * Map tester with "diagonal" path.
 */
public class TestMap {
    private final static Size TEST_SIZE = new SizeImpl(20, 20);
    private final static int ITERATION_MOVEMENT = 500;  //TODO test with SCALING_FACTOR
    private MapController map = new MapControllerImpl(TEST_SIZE, null, null);
    private LogicalPosition pos = map.getSpawnPosition();

    @Test
    void testGetNextPosition() {
        try {
            var opt = map.getNextPosition(pos, 0);
            Assertions.assertEquals(pos, opt.get());
            while (opt.isPresent()) {
                pos = opt.get();
                opt = map.getNextPosition(pos, ITERATION_MOVEMENT);
            }
        } catch (IllegalArgumentException e) {
            Assertions.fail();
        }
    }
}
