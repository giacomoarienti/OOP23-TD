package it.unibo.towerdefense.controllers;

/**
 * Interface that defines SerializableController methods.
 */
public interface SerializableController extends Controller {

    /**
     * Returns the JSON representation of it's model.
     * @return the JSON String
     */
    String toJSON();
}
