package it.unibo.towerdefense.models.scoreboard.score;

import java.util.Collections;
import java.util.Set;

import it.unibo.towerdefense.models.scoreboard.Scoreboard;

/**
 * Class implementing the Data Transfer Object pattern for the Scoreboard.
 */
public class ScoreboardDTO {

    private final Set<Score> scores;

    /**
     * Constructor for ScoreboardDTO.
     * @param scores the scores to be displayed
     */
    public ScoreboardDTO(final Scoreboard scoreboard) {
        this.scores = scoreboard.getScoreboard();
    }

    /**
     * Returns an umodifiable Set of Scores.
     * @return the scores
     */
    public Set<Score> getScores() {
        return Collections.unmodifiableSet(this.scores);
    }
}
