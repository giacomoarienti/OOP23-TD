package it.unibo.towerdefense.model.saves;

import java.util.Date;

import it.unibo.towerdefense.commons.api.JsonSerializable;

/**
 * Interface that models a game save.
 * The save is made up of:
 * - the save name
 * - the game state and statistics
 * - the game map
 * - the game entities (defenses).
 */
public interface Save extends JsonSerializable {

    /**
     * Returns the save file name.
     * @return the file name
     */
    String getFileName();

    /**
     * Returns the save date.
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
     * Returns the save object from JSON string.
     * @param jsonData the JSON representation of the save
     * @return the save object
     */
    static Save fromJson(final String jsonData) {
        return SaveImpl.fromJson(jsonData);
    }

}
