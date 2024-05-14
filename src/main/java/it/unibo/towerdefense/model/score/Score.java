package it.unibo.towerdefense.model.score;

import it.unibo.towerdefense.commons.api.JsonSerializable;

/**
 * Interface that defines the methods to manage the game's scoreboard.
 */
public interface Score extends JsonSerializable, Comparable<Score> {

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
     * Returns the score object from JSON string.
     * @param jsonData the JSON representation of the scoreboard
     * @return the score object
     */
    static Score fromJson(final String jsonData) {
        return ScoreImpl.fromJson(jsonData);
    }
}
