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

public class TestConfigurableEnemyCatalogue {

    private final static String FOLDER = "it/unibo/towerdefense/models/enemies/";

    @Test
    void testFileRead() {
        Assertions.assertDoesNotThrow(() -> new ConfigurableEnemyCatalogue(FOLDER + "types.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(FOLDER + "types1.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(FOLDER + "types2.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(FOLDER + "types3.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new ConfigurableEnemyCatalogue(FOLDER + "types4.json"));
    }

    @Nested
    public class NestedTestBlock {

        record SimpleEnemyType(EnemyLevel level, EnemyArchetype type) implements EnemyType{
            @Override
            public final boolean equals(Object o) {
                return o instanceof EnemyType &&
                    ((EnemyType)o).level() == this.level() &&
                    ((EnemyType)o).type() == this.type();
            }
            @Override
            public final int hashCode() {
                return Objects.hash(this.level(), this.type());
            }
        };

        private ConfigurableEnemyCatalogue tested;
        private Set<EnemyType> types;

        @BeforeEach
        void init(){
            tested = new ConfigurableEnemyCatalogue(FOLDER + "types.json");
            types = Arrays.stream(EnemyLevel.values())
                .flatMap( (EnemyLevel l) -> Arrays.stream(EnemyArchetype.values())
                .map( (EnemyArchetype t) -> new SimpleEnemyType(l, t))).collect(Collectors.toSet());
        }

        @Test
        void testGetEnemyTypes() {
            Assertions.assertTrue(tested.getEnemyTypes().containsAll(types));
        }

        @Test
        void testGetEnemyTypes2() {
            List<Predicate<EnemyType>> tests = List.of(
                et -> et.level() == EnemyLevel.I && et.type() == EnemyArchetype.A,
                et -> et.level() == EnemyLevel.I && et.type() == EnemyArchetype.B
            );

            tests.forEach(
                t -> Assertions.assertTrue(
                    tested.getEnemyTypes(t).containsAll(types.stream().filter(t).collect(Collectors.toSet()))
                )
            );
        }
    }
}
