package it.unibo.towerdefense.models.enemies;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class PredicateBasedRandomWaveSupplier implements WaveSupplier{

    private final WavePolicySupplier wp;
    private final Random r = new Random();

    PredicateBasedRandomWaveSupplier(final WavePolicySupplier wp){
        this.wp = wp;
    }

    @Override
    public Wave apply(final Integer wave) {
        List<EnemyType> availableTypes = List.copyOf(EnemyCatalogue.getCatalogue().getEnemyTypes(wp.getPredicate(wave)));
        List<EnemyType> order = r.ints(wp.getLength(wave), 0, availableTypes.size()).mapToObj( i -> availableTypes.get(i)).toList();
        return new ModulableWave(wp.getCyclesPerSpawn(wave), order);
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
            counter++;
            return counter % skip == 0 ? Optional.of(base.next()) : Optional.empty();
        }
    }

    private class ModulableWave implements Wave{
        private final int cyclesPerSpawn;
        private final Iterable<EnemyType> order;
        ModulableWave(final int cyclesPerSpawn, final Iterable<EnemyType> order){
            this.cyclesPerSpawn = cyclesPerSpawn;
            this.order = order;
        }
        @Override
        public Iterator<Optional<EnemyType>> iterator() {
            return new SkipIterator<>(order.iterator(), cyclesPerSpawn);
        }
    }
}
