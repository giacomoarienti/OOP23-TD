package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyLevel;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

/**
 * Tests for WavePolicySupplierImpl.
 */
class TestWavePolicySupplierImpl {

    private static final String ROOT = "it/unibo/towerdefense/models/enemies/Test_";

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
        }
        for (String s : evilFilenames) {
            String config = FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + s).toURI()));
            Assertions.assertThrows(RuntimeException.class, () -> new WavePolicySupplierImpl(config));
        }
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
        private void init() throws URISyntaxException, IOException {
            tested = new WavePolicySupplierImpl(
                    FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + TEST_FILE).toURI())));
        }

        /**
         * Tests the method getCyclesPerSpawn works as intended.
         */
        @Test
        @SuppressWarnings("checkstyle:MagicNumberCheck")
        void testGetCyclesPerSpawn() {
            Map<Integer, Integer> expected = Map.of(1, 120, 2, 120, 3, 100);
            Assertions.assertThrows(RuntimeException.class, () -> tested.getCyclesPerSpawn(0));
            Assertions.assertTrue(expected.entrySet().stream()
                    .allMatch(e -> e.getValue().equals(tested.getCyclesPerSpawn(e.getKey()))));
        }

        /**
         * Tests the method getPower works as intended.
         */
        @Test
        void testGetPower() {
            List<Long> expectedPowers = List.of(100000L, 400000L, 700000L, 1000000L);
            Assertions.assertThrows(RuntimeException.class, () -> tested.getPower(0));
            for (int i = 1; i <= expectedPowers.size(); i++) {
                Assertions.assertEquals(expectedPowers.get(i - 1), tested.getPower(i));
            }
        }

        /**
         * Tests the method getPredicate works as intended.
         */
        @Test
        void testGetPredicate() {
            Assertions.assertThrows(RuntimeException.class, () -> tested.getPredicate(0));
            Map<Integer, Set<EnemyType>> expected = Map.of(1,
                    Set.of(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.B),
                            TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.C)),
                    3,
                    Set.of(TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A),
                            TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.B),
                            TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.C)));
            Assertions.assertTrue(expected.entrySet().stream()
                    .allMatch(e -> e.getValue().stream().allMatch(tested.getPredicate(e.getKey()))));
        }
    }
}
