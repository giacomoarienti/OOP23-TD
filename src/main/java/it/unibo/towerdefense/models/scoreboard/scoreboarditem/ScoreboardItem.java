package it.unibo.towerdefense.models.scoreboard.scoreboarditem;

import it.unibo.towerdefense.models.JsonSerializable;

/**
 * Interface that defines the methods to manage the game's scoreboard.
 */
public interface ScoreboardItem extends JsonSerializable<ScoreboardItem> {

    /**
     * Returns the sccoreboard object from JSON string.
     * @param json the JSON representation of the scoreboard
     * @return the sccoreboard object
     */
    static ScoreboardItem fromJson(final String jsonData) {
        return new ScoredoardItemImpl().fromJSON(jsonData);
    }
}
