package it.unibo.towerdefense.commons.patterns;

/**
 * An interface for a simple Observer pattern.
 *
 * @param <T> the type of the source of a possible event
 */
public interface Observer<T> {
    /**
     * Method to notify the observer that the events it observes was caused by
     * source.
     *
     * @param source the source of the event
     */
    void notify(T source);
}
