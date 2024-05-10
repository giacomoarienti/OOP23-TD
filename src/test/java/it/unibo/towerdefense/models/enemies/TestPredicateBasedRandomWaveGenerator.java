package it.unibo.towerdefense.models.enemies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.enemies.EnemyType;
import it.unibo.towerdefense.utils.file.FileUtils;

/**
 * Tests for Predic
 */
public class TestPredicateBasedRandomWaveGenerator {

    private final static String ROOT = "it/unibo/towerdefense/models/enemies/Test_";
    private final static int N = 10000;
    private PredicateBasedRandomWaveGenerator rwg;
    private WavePolicySupplierImpl wps;
    private ConfigurableEnemyCatalogue catalogue;

    /**
     * Initializes the classes needed for testing.
     */
    @BeforeEach
    void init() throws URISyntaxException, IOException {
        wps = new WavePolicySupplierImpl(
                FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + "waves.json").toURI())));
        catalogue = new ConfigurableEnemyCatalogue(
                FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + "types.json").toURI())));
        rwg = new PredicateBasedRandomWaveGenerator(wps, catalogue);
    }

    /**
     * Test waves from 1 to N.
     */
    @Test
    void testWaves() {
        for (int i = -5; i < N; i++) {
            testWave(i);
        }
    }

    /**
     * Tests the given wave.
     *
     * @param wave number of the wave to test.
     */
    private void testWave(int wave) {

        if (wave < 1) {
            Assertions.assertThrows(RuntimeException.class, () -> rwg.apply(wave));
        } else {
            int length = wps.getLength(wave);
            int rate = wps.getCyclesPerSpawn(wave);
            Wave generated = rwg.apply(wave);
            for (int i = 0; i < length * rate - (rate - 1); i++) {
                Assertions.assertTrue(generated.hasNext(), () -> "Didn't have next");
                if (i % rate == 0) {
                    Optional<RichEnemyType> current = generated.next();
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
                } else {
                    Assertions.assertTrue(generated.next().isEmpty(), () -> "Was present");
                }
            }
        }

    }
}
