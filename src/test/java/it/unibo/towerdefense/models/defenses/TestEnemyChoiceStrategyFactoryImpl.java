package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;

import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    /**Setup the enemy list. */
    @BeforeEach
    void setUp(){
        testTargets = new LinkedList<>();
    }

    /**
     * Test closest targets strategy.
     */
    @Test
    void testClosestTargetStrategy() {
        EnemyChoiceStrategy strategy = factory.closestTargets(TEST_MAX_TARGETS, TEST_RANGE, TEST_POSITION);
        /**Test 1: no target possible*/
        Assertions.assertEquals(strategy.execute(List.of(), TEST_DAMAGE), Map.of());

        /**Test 2: 1 target */
        
        testTargets.add(new ImmutablePair<>(new LogicalPosition(5, 5),TEST_HP));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), Map.of(0,TEST_DAMAGE));

        /**Add unreachable targets, expect same result */
        testTargets.add(new ImmutablePair<>(new LogicalPosition(20000, -20000),TEST_HP));
        testTargets.add(new ImmutablePair<>(new LogicalPosition(-20000, 20000),TEST_HP));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), Map.of(0,TEST_DAMAGE));

        /**Add 5 reachable targets,expect closest 5 */
        testTargets.add(new ImmutablePair<>(new LogicalPosition(1, 1), TEST_HP));
        testTargets.add(new ImmutablePair<>(new LogicalPosition(2, 2), TEST_HP));
        /**Index 5:expected to not be targeted*/
        testTargets.add(new ImmutablePair<>(new LogicalPosition(7, 7), TEST_HP)); 
        testTargets.add(new ImmutablePair<>(new LogicalPosition(4, -4), TEST_HP));
        testTargets.add(new ImmutablePair<>(new LogicalPosition(3, -3), TEST_HP));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE),
        Map.of(0, TEST_DAMAGE, 3, TEST_DAMAGE, 4, TEST_DAMAGE, 6,TEST_DAMAGE, 7, TEST_DAMAGE)
        );
    }
}
