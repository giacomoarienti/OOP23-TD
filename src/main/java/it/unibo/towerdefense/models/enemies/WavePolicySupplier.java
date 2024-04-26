package it.unibo.towerdefense.models.enemies;

import java.util.function.Predicate;

public interface WavePolicySupplier {
    Predicate<EnemyType> getPredicate(final Integer wave);
    Integer getLength(final Integer wave);
    Integer getCyclesPerSpawn(final Integer wave);
}
