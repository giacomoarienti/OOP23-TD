package it.unibo.towerdefense.models.enemies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

public class TestWavePolicySupplierImpl {

    private final static String ROOT = "it/unibo/towerdefense/models/enemies/Test_";

    @Test
    void testLoadConfig(){
        Assertions.assertDoesNotThrow(() -> new WavePolicySupplierImpl(ROOT + "waves.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new WavePolicySupplierImpl(ROOT + "waves1.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new WavePolicySupplierImpl(ROOT + "waves2.json"));
        Assertions.assertThrows(RuntimeException.class, () -> new WavePolicySupplierImpl("nonexistent"));
    }

    @Nested
    class NestedTestBlock{

        private record QuickEnemyType(EnemyLevel level, EnemyArchetype type) implements EnemyType{};

        private WavePolicySupplierImpl tested;

        @BeforeEach
        void init(){
            tested = new WavePolicySupplierImpl(ROOT + "waves.json");
        }

        @Test
        void testGetCyclesPerSpawn() {
            Assertions.assertEquals(5, tested.getCyclesPerSpawn(1));
            Assertions.assertEquals(5, tested.getCyclesPerSpawn(2));
            Assertions.assertEquals(5, tested.getCyclesPerSpawn(3));
            Assertions.assertEquals(3, tested.getCyclesPerSpawn(4));
            Assertions.assertEquals(3, tested.getCyclesPerSpawn(10));
            Assertions.assertEquals(3, tested.getCyclesPerSpawn(990));
            Assertions.assertEquals(1, tested.getCyclesPerSpawn(1000));
            Assertions.assertEquals(1, tested.getCyclesPerSpawn(132094));
        }

        @Test
        void testGetLength() {
            Assertions.assertEquals(10, tested.getLength(1));
            Assertions.assertEquals(10, tested.getLength(2));
            Assertions.assertEquals(15, tested.getLength(3));
            Assertions.assertEquals(200, tested.getLength(10));
            Assertions.assertEquals(200, tested.getLength(20000));
        }

        @Test
        void testGetPredicate() {
            Assertions.assertTrue(tested.getPredicate(1).test(new QuickEnemyType(EnemyLevel.I, EnemyArchetype.B)));
            Assertions.assertTrue(tested.getPredicate(1).test(new QuickEnemyType(EnemyLevel.I, EnemyArchetype.C)));
            Assertions.assertFalse(tested.getPredicate(1).test(new QuickEnemyType(EnemyLevel.I, EnemyArchetype.A)));

            Assertions.assertFalse(tested.getPredicate(2).test(new QuickEnemyType(EnemyLevel.I, EnemyArchetype.A)));

            Assertions.assertTrue(tested.getPredicate(200).test(new QuickEnemyType(EnemyLevel.I, EnemyArchetype.B)));
            Assertions.assertTrue(tested.getPredicate(200).test(new QuickEnemyType(EnemyLevel.I, EnemyArchetype.C)));

            Assertions.assertTrue(tested.getPredicate(10000).test(new QuickEnemyType(EnemyLevel.IV, EnemyArchetype.A)));
            Assertions.assertTrue(tested.getPredicate(3809).test(new QuickEnemyType(EnemyLevel.IV, EnemyArchetype.B)));

            Assertions.assertFalse(tested.getPredicate(2000).test(new QuickEnemyType(EnemyLevel.II, EnemyArchetype.A)));

        }
    }
}
