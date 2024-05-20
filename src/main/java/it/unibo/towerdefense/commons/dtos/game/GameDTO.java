package it.unibo.towerdefense.commons.dtos.game;

import it.unibo.towerdefense.commons.api.Copyable;
import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.model.game.GameStatus;

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
     * Getter for the game status.
     * @return the game status
     */
    GameStatus getStatus();

    /**
     * Returns the GameDTO object from JSON string.
     * @param jsonData the JSON representation
     * @return the GameDTO object
     */
    static GameDTO fromJson(final String jsonData) {
        return GameDTOImpl.fromJson(jsonData);
    }

}
