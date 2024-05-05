package it.unibo.towerdefense.utils.patterns;

import java.util.Optional;
import java.util.Iterator;

/**
 * [Pattern]Decorator for an iterator which returns the next item of the
 * decorated iterator only every "skip" calls on next()
 */
public class SkipIterator<T> implements Iterator<Optional<T>> {
    private final Iterator<T> base;
    private final int skip;
    private int counter;

    /**
     * Constructor for the class.
     *
     * @param base the iterator to decorate
     * @param skip how many calls to next constitute a cycle (must be > 0)
     */
    public SkipIterator(final Iterator<T> base, int skip) {
        if(skip < 0){
            throw new IllegalArgumentException("skip must be > 0");
        }
        this.base = base;
        this.skip = skip;
        this.counter = -1;
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return base.hasNext();
    }

    /**
     * Returns an empty Optionial skip-1 times after every actual item returned.
     *
     * @return an empty Optionial or an Optional containing the next item.
     */
    @Override
    public Optional<T> next() {
        counter = (counter + 1) % skip;
        return counter == 0 ? Optional.of(base.next()) : Optional.empty();
    }
}
