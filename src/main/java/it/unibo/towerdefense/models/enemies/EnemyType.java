package it.unibo.towerdefense.models.enemies;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Interface which models an enemy in its abstract form.
 */
@FunctionalInterface
interface EnemyType {
    /**
     * The functional method.
     *
     * @return a pair containing MaxHP and Speed values
     */
    Pair<Integer, Integer> getSpecs();

    default int getMaxHP() {
        return getSpecs().getLeft();
    }

    default int getSpeed() {
        return getSpecs().getRight();
    }
}
