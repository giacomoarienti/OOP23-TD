package it.unibo.towerdefense.models.enemies;

import java.util.function.Function;

/**
 * Interface for a class that translates the wave number into an
 * actual Wave consisting in a sequence of EnemyTypes.
 */
public interface WaveSupplier extends Function<Integer, Wave>{

}
