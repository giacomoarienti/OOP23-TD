package it.unibo.towerdefense.model.saving;

import java.util.Date;

import it.unibo.towerdefense.commons.api.JsonSerializable;

/**
 * Interface that models a game saving.
 * The saving is made up of:
 * - the saving name
 * - the game state and statistics
 * - the game map
 * - the game entities (defenses).
 */
public interface Saving extends JsonSerializable {

    /**
     * Returns the saving file name.
     * @return the file name
     */
    String getFileName();

    /**
     * Returns the saving date.
     * @return the date
     */
    Date getDate();

    /**
     * Returns the game json presentation.
     * @return the game json string
     */
    String getGameJson();

    /**
     * Returns the game map json presentation.
     * @return the game map json string
     */
    String getMapJson();

    /**
     * Returns the list of game defenses json presentation.
     * @return the list of game defenses json string
     */
    String getDefensesJson();

    /**
     * Returns the saving object from JSON string.
     * @param jsonData the JSON representation of the saving
     * @return the saving object
     */
    static Saving fromJson(final String jsonData) {
        return SavingImpl.fromJson(jsonData);
    }

}
