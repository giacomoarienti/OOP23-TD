package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

/**
 * Tests for EnemiesImpl.
 */
class TestEnemiesImpl {

    /**
     * Arbitrary starting position.
     */
    private static final EnemyPosition STARTING_POSITION = new EnemyPosition(0, 0, Direction.E, 100);
    private WavePolicySupplierImpl testingWPS;
    private EnemiesImpl tested;
    private int dead;

    /**
     * Initializes the class to be tested along with some helper classes which we
     * presume already work as intended (see other tests).
     * Also instantiates some "fake" controllers which only implement the function
     * needed for testing EnemiesImpl.
     */
    @BeforeEach
    void init() throws IOException {
        testingWPS = new WavePolicySupplierImpl(
                FileUtils.readFile(ClassLoader.getSystemResourceAsStream(Filenames.wavesConfig())));
        tested = new EnemiesImpl((pos, speed) -> Optional.of(STARTING_POSITION.copy()),
                () -> STARTING_POSITION.copy());
        dead = 0;
        tested.addDeathObserver(e -> dead += 1);
    }

    /**
     * Tests the class behaves correctly right after initialization.
     */
    @Test
    void testEmpty() {
        Assertions.assertTrue(tested.getEnemies().isEmpty());
        Assertions.assertFalse(tested.isWaveActive());
        Assertions.assertDoesNotThrow(() -> tested.update());
    }

    /**
     * Tests the class behaves correctly when asked to start a new wave.
     */
    @Test
    void testSpawn() {
        Assertions.assertFalse(tested.isWaveActive());
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(0));
        Assertions.assertDoesNotThrow(() -> tested.spawn(1));
        Assertions.assertTrue(tested.isWaveActive());
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(2));
        Assertions.assertTrue(tested.isWaveActive());

    }

    /**
     * Tests the class behaves correctly on updates.
     */
    @Test
    void testUpdate() {
        tested.spawn(1);
        tested.update();
        Assertions.assertEquals(1, tested.getEnemies().size());
        tested.update();
        Assertions.assertEquals(1, tested.getEnemies().size());
    }

    /**
     * Tests the collection is correctly updated after a contained enemy dies.
     */
    @Test
    void testDied() {
        tested.spawn(1);
        tested.update();
        final RichEnemy onlyEnemy = tested.getEnemies().stream().findAny().get();
        onlyEnemy.die();
        Assertions.assertEquals(0, tested.getEnemies().size());
        Assertions.assertEquals(1, dead);
    }

    /**
     * Tests the wave behaves as it should.
     */
    @Test
    void testWaveEnd() {
        final int wave = 1;
        final long p = testingWPS.getPower(wave);
        tested.spawn(wave);
        while (tested.getEnemies().stream().reduce(0, (i, u) -> i + u.getPowerLevel(), (i1, i2) -> i1 + i2) < p) {
            Assertions.assertTrue(tested.isWaveActive());
            tested.update();
        }
        Assertions.assertTrue(tested.isWaveActive()); // Enemies are still alive
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(2));
        tested.getEnemies().stream().forEach(e -> e.die());
        Assertions.assertEquals(0, tested.getEnemies().size());
        Assertions.assertFalse(tested.isWaveActive());
        Assertions.assertDoesNotThrow(() -> tested.spawn(2));
    }
}
