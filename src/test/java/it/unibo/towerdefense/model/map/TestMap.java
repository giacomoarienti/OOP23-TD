package it.unibo.towerdefense.model.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.MapManagerImpl;
import it.unibo.towerdefense.model.map.PathVector;

/**
 * Map tester with "generate" path.
 */
public class TestMap {
    private final static Size TEST_SIZE = new SizeImpl(30, 20);
    private final static int ITERATION_MOVEMENT = 10;
    private MapManager map = new MapManagerImpl(TEST_SIZE);
    private PathVector spawn = map.getSpawnPosition();
    private LogicalPosition pos = spawn.position();
    private int distanceToEnd = spawn.distanceToEnd();

    @Test
    void testGetNextPosition() {
        System.out.println(spawn);
        try {
            var vector = map.getNextPosition(pos, 0);
            Assertions.assertEquals(pos, vector.position());
            while (vector.distanceToEnd() != 0) {
                pos = vector.position();
                //Assertions.assertTrue(distanceToEnd > vector.distanceToEnd());
                distanceToEnd = vector.distanceToEnd();
                vector = map.getNextPosition(pos, ITERATION_MOVEMENT);
                System.out.println(vector);
            }
        } catch (IllegalArgumentException e) {
            Assertions.fail();
        }
    }
}
