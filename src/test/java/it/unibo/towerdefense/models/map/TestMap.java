package it.unibo.towerdefense.models.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.controllers.map.MapControllerImpl;
import it.unibo.towerdefense.controllers.map.PathVector;

/**
 * Map tester with "generate" path.
 */
public class TestMap {
    private final static Size TEST_SIZE = new SizeImpl(20, 20);
    private final static int ITERATION_MOVEMENT = 1;
    private MapController map = new MapControllerImpl(TEST_SIZE, null);
    private PathVector spawn = map.getSpawnPosition();
    private LogicalPosition pos = spawn.position();
    private int distanceToEnd = spawn.distanceToEnd();

    @Test
    void testGetNextPosition() {
        try {
            var vector = map.getNextPosition(pos, 0);
            Assertions.assertEquals(pos, vector.position());
            while (vector.distanceToEnd() == 0) {
                pos = vector.position();
                Assertions.assertTrue(distanceToEnd > vector.distanceToEnd());
                distanceToEnd = vector.distanceToEnd();
                vector = map.getNextPosition(pos, ITERATION_MOVEMENT);
            }
        } catch (IllegalArgumentException e) {
            Assertions.fail();
        }
    }
}
