package it.unibo.towerdefense.model.defenses;

import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.enemies.Enemy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/**
 * Test class for the CollisionBoxImpl class.
 */
class TestEnemyChoiceStrategyFactoryImpl {

    private static EnemyChoiceStrategyFactory factory = new EnemyChoiceStrategyFactoryImpl();
    private static final LogicalPosition TEST_POSITION = new LogicalPosition(0, 0);
    private static final int TEST_RANGE = 10;
    private static final int TEST_MAX_TARGETS = 5;
    private static final int TEST_DAMAGE = 20;
    private static final int TEST_AREA_RANGE = 5;

    private List<Enemy> testTargets;

    /**creates a test target.
     * @param pos the position of the enemy
     * @param distance fake distance to give for testing.
     * @return a dummy implemntation of Enemy for testing.
     * WARNING : for the scope of these tests we only care about the position and distance,
     * other getters are not implemented.
    */
    private Enemy testEnemy(final LogicalPosition pos, final int distance) {
        return new Enemy() {
            @Override
            public void hurt(final int amount) {

            }
            @Override
            public int getHp() {
                return 1;
            }
            @Override
            public int getSpeed() {
                throw new UnsupportedOperationException();
            }
            @Override
            public int getValue() {
                throw new UnsupportedOperationException();
            }
            @Override
            public EnemyPosition getPosition() {
                return new EnemyPosition(pos.getX(), pos.getY(), null, distance);
            }
            @Override
            public EnemyInfo info() {
                throw new UnsupportedOperationException();
            }
            @Override
            public boolean isDead() {
                throw new UnsupportedOperationException("Unimplemented method 'isDead'");
            }
        };
    }


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

        final EnemyChoiceStrategy strategy = factory.closestTargets(TEST_MAX_TARGETS, TEST_RANGE, TEST_POSITION);
        /**Test 1: no target possible*/
        Assertions.assertEquals(strategy.execute(List.of(), TEST_DAMAGE), expectedResultTest1);
        /**Test 2: 1 target*/
        testTargets.add(testEnemy(testPos1, 0));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest2);
        /**Test 3:Add unreachable targets, expect same result */
        testTargets.add(testEnemy(testPos2, 0));
        testTargets.add(testEnemy(testPos3, 0));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest3);
        /**Test 4:Add 5 reachable targets,expect closest 5 */
        testTargets.add(testEnemy(testPos4, 0));
        testTargets.add(testEnemy(testPos5, 0));
        /**Index 5:expected to not be targeted*/
        testTargets.add(testEnemy(testPos6, 0));
        testTargets.add(testEnemy(testPos7, 0));
        testTargets.add(testEnemy(testPos8, 0));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest4);
    }

    /**
     * Test closest targets strategy.
     */
    @Test
    void testClosestTargetWithAreaDamage() {
        /**create positions for this test.*/
        final LogicalPosition testPos1 = new LogicalPosition(5, 5);
        final LogicalPosition testPos2 = new LogicalPosition(3, 3);
        final LogicalPosition testPos3 = new LogicalPosition(-12, -15);
        final LogicalPosition testPos4 = new LogicalPosition(0, 0);
        final LogicalPosition testPos5 = new LogicalPosition(4, -5);
        final LogicalPosition testPos6 = new LogicalPosition(4, 0);
        /**create expected results */
        final Map<Integer, Integer> expectedResultTest1 = new HashMap<>();
        final Map<Integer, Integer> expectedResultTest2 = Map.of(0, TEST_DAMAGE);
        final Map<Integer, Integer> expectedResultTest3 = Map.of(0, TEST_DAMAGE, 1, TEST_DAMAGE);
        final Map<Integer, Integer> expectedResultTest4 = Map.of(1, TEST_DAMAGE,
        3, TEST_DAMAGE, 5, TEST_DAMAGE);

        final EnemyChoiceStrategy strategy = factory.closestTargetWithAreaDamage(TEST_AREA_RANGE, TEST_RANGE, TEST_POSITION);
        /**Test 1: no target possible*/
        Assertions.assertEquals(strategy.execute(List.of(), TEST_DAMAGE), expectedResultTest1);
        /**Test 2: 1 target*/
        testTargets.add(testEnemy(testPos1, 0));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest2);
        /**Test 3:Add one target in the area,and one not in the area.*/
        testTargets.add(testEnemy(testPos2, 0));
        testTargets.add(testEnemy(testPos3, 0));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest3);
        /**Test 4:Add a few more entities for precision check.*/
        testTargets.add(testEnemy(testPos4, 0));
        testTargets.add(testEnemy(testPos5, 0));
        testTargets.add(testEnemy(testPos6, 0));
        Assertions.assertEquals(strategy.execute(testTargets, TEST_DAMAGE), expectedResultTest4);
    }

    @Test
    void testClosestToEndMap() {
        /**create positions for this test.*/
        final LogicalPosition testPos1 = new LogicalPosition(12, 12);
        final LogicalPosition testPos2 = new LogicalPosition(14, 14);
        final LogicalPosition testPos3 = new LogicalPosition(0, 0);
        final LogicalPosition testPos4 = new LogicalPosition(1, -1);
        final LogicalPosition testPos5 = new LogicalPosition(13, -13);
        final LogicalPosition testPos6 = new LogicalPosition(15, 14);
        /**create testDistances */
        final int testDistance1 = 7;
        final int testDistance2 = 6;
        final int testDistance3 = 1;
        final int testDistance4 = 1;
        final int testDistance5 = 2;
        final int testDistance6 = 5;
        final int testDistance7 = 3;
        final int testDistance8 = 1;
        final int testDistance9 = 8;
        final int testDistance10 = 8;
        /**Create Test Enemies.*/
        final Enemy testEnemy1 = testEnemy(testPos1, testDistance1);
        final Enemy testEnemy2 = testEnemy(testPos2, testDistance2);
        final Enemy testEnemy3 = testEnemy(testPos3, testDistance3);
        final Enemy testEnemy4 = testEnemy(testPos4, testDistance4);
        final Enemy testEnemy5 = testEnemy(testPos1, testDistance5);
        final Enemy testEnemy6 = testEnemy(testPos2, testDistance6);
        final Enemy testEnemy7 = testEnemy(testPos3, testDistance7);
        final Enemy testEnemy8 = testEnemy(testPos4, testDistance8);
        final Enemy testEnemy9 = testEnemy(testPos5, testDistance9);
        final Enemy testEnemy10 = testEnemy(testPos6, testDistance10);
        /**create expected results */
        final Map<Integer, Integer> expectedResultTest1 = Map.of(0, TEST_DAMAGE);
        final Map<Integer, Integer> expectedResultTest2 = new HashMap<>();
        final Map<Integer, Integer> expectedResultTest3 = Map.of(4, TEST_DAMAGE);

        final EnemyChoiceStrategy strategy = factory.closestToEndMap(TEST_AREA_RANGE,
        TEST_POSITION);

        /**Test 1:check closest target to custom point.*/
        testTargets.add(testEnemy1);
        testTargets.add(testEnemy2);
        Assertions.assertEquals(expectedResultTest1, strategy.execute(testTargets, TEST_DAMAGE));
        /**Test 2:check that targets in range aren't being selected.*/
        testTargets.clear();
        testTargets.add(testEnemy3);
        testTargets.add(testEnemy4);
        Assertions.assertEquals(expectedResultTest2, strategy.execute(testTargets, TEST_DAMAGE));
        /**Test 3:multiple targets,some selectable and some not.*/
        testTargets.clear();
        testTargets.add(testEnemy5);
        testTargets.add(testEnemy6);
        testTargets.add(testEnemy7);
        testTargets.add(testEnemy8);
        testTargets.add(testEnemy9);
        testTargets.add(testEnemy10);
        Assertions.assertEquals(expectedResultTest3, strategy.execute(testTargets, TEST_DAMAGE));
    }
}
