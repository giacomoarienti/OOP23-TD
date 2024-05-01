package it.unibo.towerdefense.models.enemies;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class PredicateBasedRandomWaveGenerator implements WaveSupplier{

    private final WavePolicySupplier wp;
    private final EnemyCatalogue ec;
    private final Random r = new Random();

    PredicateBasedRandomWaveGenerator(final WavePolicySupplier wp, final EnemyCatalogue ec){
        this.wp = wp;
        this.ec = ec;
    }

    @Override
    public Wave apply(final Integer wave) {
        List<RichEnemyType> availableTypes = List.copyOf(ec.getEnemyTypes(wp.getPredicate(wave)));
        return (Wave)new SkipIterator<RichEnemyType>(
                r.ints(wp.getLength(wave), 0, availableTypes.size())
                                .mapToObj( i -> availableTypes.get(i)).iterator(),
                wp.getCyclesPerSpawn(wave));
    }

    /**
     * Decorator for an iterator which returns the next item of the
     * decorated iterator only every "skip" calls on next()
     */
    private class SkipIterator<T> implements Iterator<Optional<T>>{
        private final Iterator<T> base;
        private final int skip;
        private int counter;
        SkipIterator(final Iterator<T> base, int skip){
            this.base = base;
            this.skip = skip;
            this.counter = -1;
        };
        @Override
        public boolean hasNext() {
            return base.hasNext();
        }
        @Override
        public Optional<T> next() {
            counter = (counter + 1) % skip;
            return counter == 0 ? Optional.of(base.next()) : Optional.empty();
        }
    }
}
