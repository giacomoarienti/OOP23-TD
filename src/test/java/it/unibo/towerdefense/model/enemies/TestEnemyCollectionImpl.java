package it.unibo.towerdefense.model.enemies;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyLevel;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.enemies.EnemyCollectionImpl;
import it.unibo.towerdefense.model.enemies.RichEnemy;
import it.unibo.towerdefense.model.enemies.RichEnemyType;
import it.unibo.towerdefense.model.enemies.SimpleEnemyFactory;

/**
 * Tests for EnemyCollectionImpl.
 */
public class TestEnemyCollectionImpl {

    private static final LogicalPosition STARTING_POSITION = new LogicalPosition(0, 0);
    private EnemyCollectionImpl tested;
    private SimpleEnemyFactory helper;
    private RichEnemyType t;
    private RichEnemy spawned;

    /**
     * Initializes the classes needed for testing.
     * Also initializes a "fake" gc used for testing and a SimpleEnemyFactory which
     * we assume already working.
     *
     * @see TestSimpleEnemyFactory
     */
    @BeforeEach
    private void init() {
        tested = new EnemyCollectionImpl((pos, speed) -> Optional.empty());
        helper = new SimpleEnemyFactory(STARTING_POSITION, Direction.EAST);
        t = TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100, 10000);
        spawned = helper.spawn(t);
    }

    /**
     * Tests the class works well when adding an enemy.
     */
    @Test
    void testAdd() {
        tested.add(spawned);
        Assertions.assertTrue(tested.getEnemies().contains(spawned));
    }

    /**
     * Tests the collection is updated after an enemy contained dies.
     */
    @Test
    void testAreDead() {
        tested.add(spawned);
        spawned.die();
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }

    /**
     * Tests the collection works when supplied multiple enemies.
     */
    @Test
    void testMultipleEnemies() {
        int number = 100;
        Set<RichEnemy> spawned = IntStream.range(0, number).mapToObj(i -> {
            RichEnemy e = helper.spawn(t);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        spawned.forEach(e -> e.die());
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }

    /**
     * Tests the class moves enemies correctly.
     */
    @Test
    void testMove() {
        Set<RichEnemy> spawned = IntStream.range(0, 100).mapToObj(i -> {
            RichEnemy e = helper.spawn(t);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        tested.move();
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }
}
