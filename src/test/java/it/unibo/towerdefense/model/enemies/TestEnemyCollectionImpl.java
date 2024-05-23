package it.unibo.towerdefense.model.enemies;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyLevel;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Tests for EnemyCollectionImpl.
 */
public class TestEnemyCollectionImpl {

    private static final EnemyPosition STARTING_POSITION = new EnemyPosition(0, 0, Direction.E, 100);
    private EnemyCollectionImpl tested;
    private SimpleEnemyFactory helper;
    private RichEnemyType t;
    private RichEnemy spawned;

    private class TestObserver implements Observer<Enemy> {
        List<Enemy> notified = new LinkedList<>();
        @Override
        public void notify(Enemy source) {
            this.notified.add(source);
        }
    }

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
        helper = new SimpleEnemyFactory();
        t = TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100, 10000);
        spawned = helper.spawn(t, STARTING_POSITION);
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
     * Tests the collection works when supplied multiple enemies
     * and the added observer is correctly signaled.
     */
    @Test
    void testMultipleEnemies() {
        int number = 100;
        TestObserver to = new TestObserver();
        tested.addDeathObserver(to);
        Set<RichEnemy> spawned = IntStream.range(0, number).mapToObj(i -> {
            RichEnemy e = helper.spawn(t, STARTING_POSITION);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        Assertions.assertTrue(to.notified.isEmpty());
        spawned.forEach(e -> e.die());
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
        Assertions.assertTrue(to.notified.containsAll(spawned));
    }

    /**
     * Tests the class moves enemies correctly.
     */
    @Test
    void testMove() {
        Set<RichEnemy> spawned = IntStream.range(0, 100).mapToObj(i -> {
            RichEnemy e = helper.spawn(t, STARTING_POSITION);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        tested.move();
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }
}
