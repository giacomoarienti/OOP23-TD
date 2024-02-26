package it.unibo.towerdefense.models;

/**
 * Interface that models a JSON serializable object.
 */
public interface JsonSerializable<T> {

    /**
     * Returns the object as a JSON string.
     * @return the JSON string
     */
    String toJSON();

    /**
     * Returns the object from the JSON representation.
     * @param jsonData the JSON representation of the object
     * @return the object
     */
    T fromJSON(String jsonData);
}
