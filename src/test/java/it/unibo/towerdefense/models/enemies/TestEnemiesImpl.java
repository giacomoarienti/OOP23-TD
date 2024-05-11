package it.unibo.towerdefense.models.enemies;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.utils.file.FileUtils;

/**
 * Tests for EnemiesImpl.
 */
public class TestEnemiesImpl {

    /**
     * Arbitrary starting position.
     */
    private static final LogicalPosition STARTING_POSITION = new LogicalPosition(0, 0);
    private WavePolicySupplierImpl testing_wp;
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
        testing_wp = new WavePolicySupplierImpl(FileUtils.readFile(Filenames.wavesConfig()));
        tested = new EnemiesImpl((pos, speed) -> Optional.empty(), STARTING_POSITION);
        dead = 0;
        tested.addDeathObserver(e -> dead += 1);
    }

    /**
     * Tests the class behaves correctly right after initialization.
     */
    @Test
    void testEmpty() {
        Assertions.assertTrue(tested.getEnemies().isEmpty());
        Assertions.assertTrue(tested.getEnemiesInfo().isEmpty());
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
        Assertions.assertTrue(tested.getEnemies().size() == 1);
        Assertions.assertTrue(tested.getEnemiesInfo().size() == 1);
        tested.update();
        // Enemy should have died since no further position is available.
        Assertions.assertTrue(tested.getEnemies().size() == 0);
        Assertions.assertTrue(tested.getEnemiesInfo().size() == 0);
    }

    /**
     * Tests the collection is correctly updated after a contained enemy dies.
     */
    @Test
    void testDied() {
        tested.spawn(1);
        tested.update();
        RichEnemy onlyEnemy = tested.getEnemies().stream().findAny().get();
        onlyEnemy.die();
        Assertions.assertTrue(tested.getEnemies().size() == 0);
        Assertions.assertTrue(tested.getEnemiesInfo().size() == 0);
        Assertions.assertEquals(1, dead);
    }

    /**
     * Tests the wave behaves as it should.
     */
    @Test
    void testWaveEnd() {
        int wave = 1;
        int l = testing_wp.getLength(wave);
        int r = testing_wp.getCyclesPerSpawn(wave);
        tested.spawn(wave);
        for (int i = 0; i < l * r - (r - 1); i++) {
            tested.update();
            System.out.println(tested.getEnemies());
        }
        Assertions.assertTrue(tested.getEnemies().size() == 1);
        Assertions.assertTrue(tested.isWaveActive()); // An enemy is still alive
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(2));
        tested.update();
        Assertions.assertTrue(tested.getEnemies().size() == 0);
        Assertions.assertFalse(tested.isWaveActive());
        Assertions.assertDoesNotThrow(() -> tested.spawn(2));
    }
}
