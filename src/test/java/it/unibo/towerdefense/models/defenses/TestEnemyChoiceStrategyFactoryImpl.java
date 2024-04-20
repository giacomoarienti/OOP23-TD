package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;

import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;


/**
 * Test class for the CollisionBoxImpl class.
 */
class TestEnemyChoiceStrategyFactoryImpl {

    private static EnemyChoiceStrategyFactory factory = new EnemyChoiceStrategyFactoryImpl();
    private static final LogicalPosition TEST_POSITION = new LogicalPosition(0, 0);
    private static final int TEST_RANGE = 10;
    private static final int TEST_MAX_TARGETS = 5;
    private static final int TEST_DAMAGE = 20;
    private static final int TEST_HP = 100;

    private List<Pair<LogicalPosition, Integer>> testTargets;
    private Iterator<LogicalPosition> testPositions;
    /**Setup the enemy list. */
    @BeforeEach
    void setUp() {
        testTargets = new LinkedList<>();
    }

    /**
     * Test closest targets strategy.
     */
    @Test
    void testClosestTargetStrategy() {
        /**create positions for this test.*/
        final LogicalPosition testPos1 = new LogicalPosition(5, 5);
        final LogicalPosition testPos2 = new LogicalPosition(2000, 2000);
        final LogicalPosition testPos3 = new LogicalPosition(-2000, -2000);
        final LogicalPosition testPos4 = new LogicalPosition(1, 1);
        final LogicalPosition testPos5 = new LogicalPosition(2, 2);
        final LogicalPosition testPos6 = new LogicalPosition(7, 7);
        final LogicalPosition testPos7 = new LogicalPosition(4, 4);
        final LogicalPosition testPos8 = new LogicalPosition(4, 4);
        /**create expected results */
        final Map<Integer, Integer> expectedResultTest1 = new HashMap<>();
        final Map<Integer, Integer> expectedResultTest2 = Map.of(0, TEST_DAMAGE);
        final Map<Integer, Integer> expectedResultTest3 = Map.of(0, TEST_DAMAGE);
        final Map<Integer, Integer> expectedResultTest4 = 
        Map.of(0, TEST_DAMAGE, 3, TEST_DAMAGE, 4, TEST_DAMAGE, 6, TEST_DAMAGE, 7, TEST_DAMAGE);

        testPositions = List.of(testPos1, testPos2, testPos3, testPos4, testPos5,
        testPos6, testPos7, testPos8).iterator();    
        EnemyChoiceStrategy strategy = factory.closestTargets(TEST_MAX_TARGETS, TEST_RANGE, TEST_POSITION);
        /**Test 1: no target possible*/
        Assertions.assertEquals(strategy.execute(List.of(), TEST_DAMAGE), expectedResultTest1);
        /**Test 2: 1 target*/
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest2);
        /**Test 3:Add unreachable targets, expect same result */
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest3);
        /**Test 4:Add 5 reachable targets,expect closest 5 */
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        /**Index 5:expected to not be targeted*/
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP)); 
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        testTargets.add(new ImmutablePair<>(testPositions.next(), TEST_HP));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest4);
    }
}
