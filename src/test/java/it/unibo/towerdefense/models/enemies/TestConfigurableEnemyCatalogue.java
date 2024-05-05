package it.unibo.towerdefense.models.enemies;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * Test for ConfigurableEnemyCataloge.
 */
public class TestConfigurableEnemyCatalogue {

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
    void testFileRead() {
        Assertions.assertDoesNotThrow(() -> new ConfigurableEnemyCatalogue(ROOT + "types.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(ROOT + "types1.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(ROOT + "types2.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(ROOT + "types3.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(ROOT + "types4.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(ROOT + "nonexistent"));
    }

    /**
     * Tests on functionality.
     */
    @Nested
    public class NestedTestBlock {

        /**
         * EnemyType composed of level and type.
         */
        record SimpleEnemyType(EnemyLevel level, EnemyArchetype type) implements EnemyType {
            @Override
            public final boolean equals(Object o) {
                return o instanceof EnemyType &&
                        ((EnemyType) o).level() == this.level() &&
                        ((EnemyType) o).type() == this.type();
            }

            @Override
            public final int hashCode() {
                return Objects.hash(this.level(), this.type());
            }
        };

        private ConfigurableEnemyCatalogue tested;
        private Set<EnemyType> types;

        /**
         * Initializes a new ConfigurableEnemyCatalogue and loads all different types in
         * the game.
         */
        @BeforeEach
        void init() {
            tested = new ConfigurableEnemyCatalogue(ROOT + "types.json");
            types = Arrays.stream(EnemyLevel.values())
                    .flatMap((EnemyLevel l) -> Arrays.stream(EnemyArchetype.values())
                            .map((EnemyArchetype t) -> new SimpleEnemyType(l, t)))
                    .collect(Collectors.toSet());
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
