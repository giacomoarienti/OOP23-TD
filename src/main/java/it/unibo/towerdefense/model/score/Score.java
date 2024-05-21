package it.unibo.towerdefense.model.score;

import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.commons.dtos.score.ScoreDTO;

/**
 * Interface that defines the methods to manage the game's scoreboard.
 */
public interface Score extends JsonSerializable {

    /**
     * Returns the name of the player.
     * @return the name of the player
     */
    String getName();

    /**
     * Returns the wave reached by the player.
     * @return the wave number
     */
    int getWave();

    /**
     * Returns the DTO representation of the score.
     * @return the DTO object
     */
    ScoreDTO toDTO();

    /**
     * Returns the score object from JSON string.
     * @param jsonData the JSON representation of the scoreboard
     * @return the score object
     */
    static Score fromJson(final String jsonData) {
        return ScoreImpl.fromJson(jsonData);
    }
}
