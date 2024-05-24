package it.unibo.towerdefense.model.map;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;

/**
 * Map tester with "generate" path.
 */
public class TestMap {
    private final static Size TEST_SIZE = new SizeImpl(20, 20);
    private final static int ITERATION_MOVEMENT = 100;
    private MapManager map = new MapManagerImpl(TEST_SIZE);
    private PathVector spawn = map.getSpawnPosition();
    private LogicalPosition pos = spawn.position();
    private int distanceToEnd = spawn.distanceToEnd();

    @Test
    void testGeneration() {
        int n = 0;
        for (int i = 0; i < 1000; i++) {
            n += new MapManagerImpl(TEST_SIZE).getMap().filter(c -> c.isPathCell()).count() < 20 ? 1 : 0;
        }
        System.out.println("Path shortest than 30 cell: " + n*0.1 + "%");
    }

    @Test
    void testSerializable() {
        String jsondata = map.toJSON();
        map = new MapManagerImpl(jsondata);
        Assertions.assertEquals(spawn, map.getSpawnPosition());
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
