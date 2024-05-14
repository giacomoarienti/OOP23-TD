package it.unibo.towerdefense.models;

import it.unibo.towerdefense.utils.patterns.Observer;

/**
 * Interface for a model that can notify its observers.
 * @param <T> the type of the source of a possible event
 */
public interface ObservableModel<T> {
    /**
     * Add an observer to the list of observers.
     * @param observer the observer to be added
     */
    void addObserver(Observer<T> observer);

    /**
     * Notify all subscribed observers.
     */
    void notifyObservers();

}
