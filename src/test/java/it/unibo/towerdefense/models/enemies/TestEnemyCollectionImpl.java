package it.unibo.towerdefense.models.enemies;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;

/**
 * Tests for EnemyCollectionImpl.
 */
public class TestEnemyCollectionImpl {

    private static final LogicalPosition STARTING_POSITION = new LogicalPosition(0, 0);
    private EnemyCollectionImpl tested;
    private SimpleEnemyFactory helper;

    /**
     * Initializes the classes needed for testing.
     * Also initializes a "fake" gc used for testing and a SimpleEnemyFactory which
     * we assume already working.
     *
     * @see TestSimpleEnemyFactory
     */
    @BeforeEach
    void init() {
        tested = new EnemyCollectionImpl((pos, speed) -> Optional.empty());
        helper = new SimpleEnemyFactory(STARTING_POSITION);
    }

    /**
     * Tests the class works well when adding an enemy.
     */
    @Test
    void testAdd() {
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy spawned = helper.spawn(t);
        tested.add(spawned);
        Assertions.assertTrue(tested.getEnemies().contains(spawned));
        Assertions.assertTrue(tested.getEnemiesInfo().contains(spawned.info()));
    }

    /**
     * Tests the collection is updated after an enemy contained dies.
     */
    @Test
    void testAreDead() {
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy spawned = helper.spawn(t);
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
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Set<Enemy> spawned = IntStream.range(0, number).mapToObj(i -> {
            Enemy e = helper.spawn(t);
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
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Set<Enemy> spawned = IntStream.range(0, 100).mapToObj(i -> {
            Enemy e = helper.spawn(t);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        tested.move();
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }
}
