package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyLevel;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;
import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.model.enemies.EnemyCatalogue;
import it.unibo.towerdefense.model.enemies.EnemyCatalogueFactory;

/**
 * Test for ConfigurableEnemyCataloge.
 */
public class TestEnemyCatalogueFactory {

    /**
     * Common prefix for all test files.
     */
    private final static String ROOT = "it/unibo/towerdefense/models/enemies/Test_";

    /**
     * Tests wheter the class can successfully load a configuration from a
     * well-formatted file and
     * throw an exception if the file is ill-formatted.
     */
    @Test
    void testFileRead() throws URISyntaxException, IOException {
        List<String> goodFilenames = List.of("types.json");
        List<String> evilFilenames = List.of("types1.json", "types2.json", "types3.json", "types4.json");
        for (String s : goodFilenames) {
            String config = FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + s).toURI()));
            Assertions.assertDoesNotThrow(() -> new EnemyCatalogueFactory(config));
        }
        ;
        for (String s : evilFilenames) {
            String config = FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + s).toURI()));
            Assertions.assertThrows(RuntimeException.class, () -> new EnemyCatalogueFactory(config));
        }
        ;
    }

    /**
     * Tests on functionality.
     */
    @Nested
    public class NestedTestBlock {

        private final static String TEST_FILE = "types.json";
        private EnemyCatalogue tested;
        private Set<EnemyType> types;

        /**
         * Initializes a new ConfigurableEnemyCatalogue and loads all different types in
         * the game.
         */
        @BeforeEach
        void init() throws URISyntaxException, IOException {
            tested = new EnemyCatalogueFactory(
                    FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + TEST_FILE).toURI()))).compile();
            types = EnemyType.getEnemyTypes();
        }

        /**
         * Tests the catalogue contains all types.
         */
        @Test
        void testGetEnemyTypes() {
            Assertions.assertTrue(tested.getEnemyTypes().containsAll(types));
        }

        /**
         * Tests the catalogue predicate-based getter works.
         */
        @Test
        void testGetEnemyTypes2() {
            List<Predicate<EnemyType>> tests = List.of(
                    et -> et.level() == EnemyLevel.I && et.type() == EnemyArchetype.A,
                    et -> et.level() == EnemyLevel.I && et.type() == EnemyArchetype.B,
                    et -> (et.level() == EnemyLevel.II && et.type() == EnemyArchetype.B)
                            || (et.level() == EnemyLevel.III && et.type() == EnemyArchetype.C));

            tests.forEach(
                    t -> Assertions.assertTrue(
                            tested.getEnemyTypes(t).containsAll(types.stream().filter(t).collect(Collectors.toSet()))));
        }
    }
}