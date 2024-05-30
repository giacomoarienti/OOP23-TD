package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

/**
 * Tests for PredicateBasedRandomWaveGenerator.
 */
class TestPredicateBasedRandomWaveGenerator {

    private static final String ROOT = "it/unibo/towerdefense/models/enemies/Test_";
    private static final int START = -5;
    private static final int END = 100;
    private PredicateBasedRandomWaveGenerator rwg;
    private WavePolicySupplierImpl wps;

    /**
     * Initializes the classes needed for testing.
     */
    @BeforeEach
    void init() throws URISyntaxException, IOException {
        wps = new WavePolicySupplierImpl(
                FileUtils.readResource(ROOT + "waves.json"));
        final EnemyCatalogue catalogue = new EnemyCatalogueFactory(
                FileUtils.readResource(ROOT + "types.json")).compile();
        rwg = new PredicateBasedRandomWaveGenerator(wps, catalogue);
    }

    /**
     * Test waves from START to END.
     */
    @Test
    void testWaves() {
        IntStream.range(START, END + 1).forEach(i -> testWave(i));
    }

    /**
     * Tests the given wave.
     *
     * @param wave number of the wave to test.
     */
    private void testWave(final int wave) {
        if (wave < 1) {
            Assertions.assertThrows(RuntimeException.class, () -> rwg.apply(wave));
        } else {
            final long power = wps.getPower(wave);
            final int rate = wps.getCyclesPerSpawn(wave);
            final Wave generated = rwg.apply(wave);
            long p = 0;
            for (int i = 0; p < power && generated.hasNext(); i++) {
                Assertions.assertTrue(generated.hasNext(), () -> "Didn't have next");
                if (i % rate == 0) {
                    final Optional<RichEnemyType> current = generated.next();
                    Assertions.assertTrue(current.isPresent(), () -> "Was not present");
                    Assertions.assertTrue(
                            wps.getPredicate(wave)
                                    .test(new EnemyType() {
                                        @Override
                                        public EnemyLevel level() {
                                            return current.get().level();
                                        }

                                        @Override
                                        public EnemyArchetype type() {
                                            return current.get().type();
                                        }
                                    }),
                            () -> "Was not right type");
                    p += current.get().getPowerLevel();
                } else {
                    Assertions.assertTrue(generated.next().isEmpty(), () -> "Was present");
                }
            }
            /*
             * any more enemy would make p > power
             */
            Assertions.assertFalse(generated.hasNext());
        }

    }
}
