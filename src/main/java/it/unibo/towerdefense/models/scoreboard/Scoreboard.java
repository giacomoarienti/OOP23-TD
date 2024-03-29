package it.unibo.towerdefense.models.scoreboard;

import java.io.IOException;
import java.util.Set;

import it.unibo.towerdefense.models.scoreboard.score.Score;

/**
 * That defines the game's scoreboard that loads
 * and supplies an ordered set of Scores.
 */
public interface Scoreboard {

    /**
     * Returns a unmodifiable ordered set of the loaded Scores.
     * @return the ordered set
     */
    Set<Score> getScoreboard();

    /**
     * Reads and loads the score from the local storage.
     * @throws IOException if the file cannot be read
     */
    void loadScores() throws IOException;

    /**
     * Saves the current score to the local storage.
     * @param name the name of the player
     * @param wave the wave reached by the player
     * @return true if score was saved
     * @throws IOException if the file cannot be read
     */
    boolean saveScore(String name, int wave) throws IOException;
}
