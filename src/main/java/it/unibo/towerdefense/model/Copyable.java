package it.unibo.towerdefense.model;

/**
 * Interface for copyable objects.
 * @param <T> the type of the object to clone
 */
public interface Copyable<T> {
    /**
     * Copies the object.
     * @return a copy of the object
     */
    T copy();
}
