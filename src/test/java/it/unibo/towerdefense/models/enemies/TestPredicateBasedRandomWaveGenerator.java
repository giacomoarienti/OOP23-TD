package it.unibo.towerdefense.models.enemies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPredicateBasedRandomWaveGenerator {

    private final static String ROOT = "it/unibo/towerdefense/models/enemies/Test_";
    private PredicateBasedRandomWaveGenerator rwg;

    @BeforeEach
    void init(){
        rwg = new PredicateBasedRandomWaveGenerator(new WavePolicySupplierImpl(ROOT + "waves.json"), new ConfigurableEnemyCatalogue(ROOT + "types.json"));
    }

    @Test
    void testApply() {
        rwg.apply(1);
    }
}
