package it.unibo.towerdefense.model.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;

/**
 * Map tester with "generate" path.
 */
class TestMap {
    private static final Size TEST_SIZE = new SizeImpl(20, 20);
    private static final int ITERATION_MOVEMENT = 36;
    private final MapManager map = new MapManagerImpl(TEST_SIZE);
    private final PathVector spawn = map.getSpawnPosition();
    private LogicalPosition pos = spawn.position();
    private int distanceToEnd = spawn.distanceToEnd();

    @Test
    void testSerializable() {
        final String jsondata = map.toJSON();
        Assertions.assertEquals(spawn,  new MapManagerImpl(jsondata).getSpawnPosition());
    }

    @Test
    void testGetNextPosition() {
        try {
            var vector = map.getNextPosition(pos, ITERATION_MOVEMENT);
            while (vector.distanceToEnd() != 0) {
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
