package it.unibo.towerdefense.models.scoreboard.score;

import it.unibo.towerdefense.models.JsonSerializable;

/**
 * Interface that defines the methods to manage the game's scoreboard.
 */
public interface Score extends JsonSerializable<Score> {

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
     * Returns the sccoreboard object from JSON string.
     * @param jsonData the JSON representation of the scoreboard
     * @return the sccoreboard object
     */
    static Score fromJson(final String jsonData) {
        return new ScoreImpl().fromJSON(jsonData);
    }
}
