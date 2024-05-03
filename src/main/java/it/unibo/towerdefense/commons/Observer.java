package it.unibo.towerdefense.commons;

/**
 * An interface for a simple Observer pattern.
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
