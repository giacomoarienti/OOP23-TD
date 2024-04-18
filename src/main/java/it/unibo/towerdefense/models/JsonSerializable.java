package it.unibo.towerdefense.models;

/**
 * Interface that models a JSON serializable object.
 * The class that implements this interface should implement
 * a static method T fromJson(String jsonData) that returns
 * the object from the JSON representation.
 */
public interface JsonSerializable {

    /**
     * Returns the object as a JSON string.
     * @return the JSON string
     */
    String toJSON();
}
