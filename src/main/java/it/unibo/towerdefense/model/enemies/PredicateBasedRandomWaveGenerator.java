package it.unibo.towerdefense.model.enemies;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

import it.unibo.towerdefense.commons.patterns.SkipIterator;

/**
 * A class responsible of producing a new wave given its ordinal number.
 * A wave cannot be produced empty.
 *
 * @see Function
 */
class PredicateBasedRandomWaveGenerator implements Function<Integer, Wave> {

    private final WavePolicySupplier wp;
    private final EnemyCatalogue ec;
    private final Random r = new Random();

    /**
     * The constructor for the class.
     *
     * @param wp
     * @param ec
     */
    PredicateBasedRandomWaveGenerator(final WavePolicySupplier wp, final EnemyCatalogue ec) {
        this.wp = wp;
        this.ec = ec;
    }

    /**
     * Returns a Wave corresponding to the sequence number given as parameter.
     *
     * @param wave the number of the wave to return
     */
    @Override
    public Wave apply(final Integer wave) {
        if (wave < 1) {
            throw new IllegalArgumentException("Wave numbers < 1 are not defined.");
        }

        final List<RichEnemyType> availableTypes = List.copyOf(ec.getEnemyTypes(wp.getPredicate(wave)));
        if (availableTypes.isEmpty()) {
            throw new RuntimeException("No available types for wave " + wave);
        }

        /*
         * randomly generates a wave with the permitted types until the sum of the
         * powerlevels of the enemy which compose the wave surpass the limit for the
         * given wave
         */
        return new SkipWave(
                r.ints(0, availableTypes.size())
                    .mapToObj(i -> availableTypes.get(i))
                    .takeWhile(new Predicate<RichEnemyType>() {
                        private int power = 0;
                        @Override
                        public boolean test(RichEnemyType t) {
                            power += t.getPowerLevel();
                            return power <= wp.getPower(wave);
                        }
                    }).iterator(),
                wp.getCyclesPerSpawn(wave));
    }

    /**
     * Class created to inherit both from SkipIterator and Wave.
     */
    private class SkipWave extends SkipIterator<RichEnemyType> implements Wave {
        SkipWave(final Iterator<RichEnemyType> base, final int skip) {
            super(base, skip);
        }
    }
}
