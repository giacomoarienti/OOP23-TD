package it.unibo.towerdefense.models.scoreboard;

import java.util.Set;

import it.unibo.towerdefense.models.scoreboard.score.Score;

/**
 * That defines the game's scoreboard that loads
 * and supplies an ordered set of Scores.
 */
public interface Scoreboard {

    /**
     * Returns an ordered set of the loaded Scores.
     * @return the ordered set
     */
    Set<Score> getScoreboard();

    /**
     * Loads the past scores from the local storage.
     */
    void loadScores();
}
