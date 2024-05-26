package it.unibo.towerdefense.model.scoreboard;

import java.io.IOException;
import java.util.List;

/**
 * That defines the game's scoreboard that loads
 * and supplies an ordered set of Scores.
 */
public interface Scoreboard {

    /**
     * Returns a unmodifiable list of the loaded Scores.
     * @return the list of Scores
     */
    List<Score> getScoreboard();

    /**
     * Reads and loads the score from the local storage.
     * @throws IOException if the file cannot be read
     */
    void loadScores() throws IOException;

    /**
     * Saves the current score to the local storage.
     * @param name the name of the player
     * @param wave the wave reached by the player
     * @return the saved score
     * @throws IOException if the file cannot be read
     */
    Score saveScore(String name, int wave) throws IOException;
}
