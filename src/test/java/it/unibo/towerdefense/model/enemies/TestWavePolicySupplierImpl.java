package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;
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

    private static final String ROOT = "src/test/resources/it/unibo/towerdefense/models/enemies/Test_";

    /**
     * Tests the class can correctly load a configuration from a well formatted file
     * and throw an exception when provided an ill-formatted one.
     */
    @Test
    void testLoadConfig() throws URISyntaxException, IOException {
        final List<String> goodFilenames = List.of("waves.json");
        final List<String> evilFilenames = List.of("waves1.json", "waves2.json", "waves3.json", "waves4.json", "waves5.json");
        for (final String s : goodFilenames) {
            final String config = FileUtils.readFile(ROOT + s);
            Assertions.assertDoesNotThrow(() -> new WavePolicySupplierImpl(config));
        }
        for (final String s : evilFilenames) {
            final String config = FileUtils.readFile(ROOT + s);
            Assertions.assertThrows(RuntimeException.class, () -> new WavePolicySupplierImpl(config));
        }
    }

    /**
     * Tests which require an initialized class.
     */
    @Nested
    class NestedTestBlock {

        private static final long WAVE4 = 1_000_000L;
        private static final long WAVE3 = 700_000L;
        private static final long WAVE2 = 400_000L;
        private static final long WAVE1 = 100_000L;
        private static final int V3 = 100;
        private static final int K3 = 3;
        private static final int K2 = 2;
        private static final int V1 = 120;
        private static final int K1 = 1;
        private static final String TEST_FILE = "waves.json";
        private WavePolicySupplierImpl tested;

        /**
         * Initializes the class for testing.
         */
        @BeforeEach
        void init() throws URISyntaxException, IOException {
            tested = new WavePolicySupplierImpl(
                    FileUtils.readFile(ROOT + TEST_FILE));
        }

        /**
         * Tests the method getCyclesPerSpawn works as intended.
         */
        @Test
        void testGetCyclesPerSpawn() {
            final Map<Integer, Integer> expected = Map.of(K1, V1, K2, V1, K3, V3);
            Assertions.assertThrows(RuntimeException.class, () -> tested.getCyclesPerSpawn(0));
            Assertions.assertTrue(expected.entrySet().stream()
                    .allMatch(e -> e.getValue().equals(tested.getCyclesPerSpawn(e.getKey()))));
        }

        /**
         * Tests the method getPower works as intended.
         */
        @Test
        @SuppressWarnings("checkstyle:magicnumbercheck")
        void testGetPower() {
            final List<Long> expectedPowers = List.of(WAVE1, WAVE2, WAVE3, WAVE4);
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
            final Map<Integer, Set<EnemyType>> expected = Map.of(1,
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
