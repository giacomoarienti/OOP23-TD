package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyLevel;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

/**
 * Tests for WavePolicySupplierImpl.
 */
public class TestWavePolicySupplierImpl {

    private final static String ROOT = "it/unibo/towerdefense/models/enemies/Test_";

    /**
     * Tests the class can correctly load a configuration from a well formatted file
     * and throw an exception when provided an ill-formatted one.
     */
    @Test
    void testLoadConfig() throws URISyntaxException, IOException {
        List<String> goodFilenames = List.of("waves.json");
        List<String> evilFilenames = List.of("waves1.json", "waves2.json", "waves3.json", "waves4.json", "waves5.json");
        for (String s : goodFilenames) {
            String config = FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + s).toURI()));
            Assertions.assertDoesNotThrow(() -> new WavePolicySupplierImpl(config));
        };
        for (String s : evilFilenames) {
            String config = FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + s).toURI()));
            Assertions.assertThrows(RuntimeException.class, () -> new WavePolicySupplierImpl(config));
        };
    }

    /**
     * Tests which require an initialized class.
     */
    @Nested
    class NestedTestBlock {

        private final static String TEST_FILE = "waves.json";
        private WavePolicySupplierImpl tested;

        /**
         * Initializes the class for testing.
         */
        @BeforeEach
        void init() throws URISyntaxException, IOException {
            tested = new WavePolicySupplierImpl(
                    FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + TEST_FILE).toURI())));
        }

        /**
         * Tests the method getCyclesPerSpawn works as intended.
         */
        @Test
        void testGetCyclesPerSpawn() {
            Assertions.assertThrows(RuntimeException.class, () -> tested.getCyclesPerSpawn(0));
            Assertions.assertEquals(5, tested.getCyclesPerSpawn(1));
            Assertions.assertEquals(5, tested.getCyclesPerSpawn(2));
            Assertions.assertEquals(5, tested.getCyclesPerSpawn(3));
            Assertions.assertEquals(3, tested.getCyclesPerSpawn(4));
            Assertions.assertEquals(3, tested.getCyclesPerSpawn(10));
            Assertions.assertEquals(3, tested.getCyclesPerSpawn(990));
            Assertions.assertEquals(1, tested.getCyclesPerSpawn(1000));
            Assertions.assertEquals(1, tested.getCyclesPerSpawn(132094));
        }

        /**
         * Tests the method getPower works as intended.
         */
        @Test
        void testGetPower() {
            Assertions.assertThrows(RuntimeException.class, () -> tested.getPower(0));
            Assertions.assertEquals(10000, tested.getPower(1));
            Assertions.assertEquals(10000, tested.getPower(2));
            Assertions.assertEquals(15000, tested.getPower(3));
            Assertions.assertEquals(200000, tested.getPower(10));
            Assertions.assertEquals(200000, tested.getPower(20000));
        }

        /**
         * Tests the method getPredicate works as intended.
         */
        @Test
        void testGetPredicate() {
            Assertions.assertThrows(RuntimeException.class, () -> tested.getPredicate(0));
            Assertions.assertTrue(tested.getPredicate(1).test(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.B)));
            Assertions.assertTrue(tested.getPredicate(1).test(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.C)));
            Assertions.assertFalse(tested.getPredicate(1).test(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A)));

            Assertions.assertFalse(tested.getPredicate(2).test(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A)));

            Assertions.assertTrue(tested.getPredicate(200).test(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.B)));
            Assertions.assertTrue(tested.getPredicate(200).test(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.C)));

            Assertions.assertTrue(tested.getPredicate(10000).test(TestingEnemyType.build(EnemyLevel.IV, EnemyArchetype.A)));
            Assertions.assertTrue(tested.getPredicate(3809).test(TestingEnemyType.build(EnemyLevel.IV, EnemyArchetype.B)));

            Assertions.assertFalse(tested.getPredicate(2000).test(TestingEnemyType.build(EnemyLevel.II, EnemyArchetype.A)));
        }
    }
}
