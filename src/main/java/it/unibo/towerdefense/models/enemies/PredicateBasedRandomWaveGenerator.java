package it.unibo.towerdefense.models.enemies;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import it.unibo.towerdefense.utils.patterns.SkipIterator;

/**
 * A class responsible of producing a new wave given its ordinal number.
 *
 * @see Function
 */
public class PredicateBasedRandomWaveGenerator implements Function<Integer, Wave> {

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
        List<RichEnemyType> availableTypes = List.copyOf(ec.getEnemyTypes(wp.getPredicate(wave)));
        return new SkipWave(
                r.ints(wp.getLength(wave), 0, availableTypes.size())
                        .mapToObj(i -> availableTypes.get(i)).iterator(),
                wp.getCyclesPerSpawn(wave));
    }

    private class SkipWave extends SkipIterator<RichEnemyType> implements Wave{
        SkipWave(Iterator<RichEnemyType> base, int skip) {
            super(base, skip);
        }
    }
}
