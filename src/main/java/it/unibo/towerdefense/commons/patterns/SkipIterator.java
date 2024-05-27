package it.unibo.towerdefense.commons.patterns;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Iterator;

/**
 * Decorator for an iterator which returns the next item of the
 * decorated iterator only every "skip" calls on next().
 *
 * @param <T> the type of the decorated iterator
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
    @SuppressFBWarnings(
        value = "EI2",
        justification = """
            Base iterator is intentionally mutable (for instance in the case of
            a lazily and randomly generated iterator) and supposedly safe to store.
                """)
    public SkipIterator(final Iterator<T> base, final int skip) {
        if (skip < 0) {
            throw new IllegalArgumentException("skip must be > 0");
        }
        this.base = base;
        this.skip = skip;
        this.counter = -1;
    }

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
