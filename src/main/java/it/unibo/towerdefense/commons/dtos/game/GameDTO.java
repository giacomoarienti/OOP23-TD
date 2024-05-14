package it.unibo.towerdefense.commons.dtos.game;

import it.unibo.towerdefense.model.Copyable;
import it.unibo.towerdefense.model.JsonSerializable;

/**
 * Data transfer object for Game.
 */
public interface GameDTO extends JsonSerializable, Copyable<GameDTO> {

    /**
     * Getter for the lives.
     * @return the amount of lives of the player
     */
    int getLives();

    /**
     * Getter for the money.
     * @return the amount of money the player has
     */
    int getMoney();

    /**
     * Getter for the wave.
     * @return the wave number
     */
    int getWave();

    /**
     * Getter for the playerName.
     * @return the player's name
     */
    String getPlayerName();

    /**
     * Returns the GameDTO object from JSON string.
     * @param jsonData the JSON representation
     * @return the GameDTO object
     */
    static GameDTO fromJson(final String jsonData) {
        return GameDTOImpl.fromJson(jsonData);
    }

}
