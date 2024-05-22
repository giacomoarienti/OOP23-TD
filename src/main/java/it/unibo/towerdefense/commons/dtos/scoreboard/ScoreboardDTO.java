package it.unibo.towerdefense.commons.dtos.scoreboard;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.towerdefense.model.score.Score;
import it.unibo.towerdefense.model.scoreboard.Scoreboard;

/**
 * Class implementing the Data Transfer Object pattern for the Scoreboard.
 */
public class ScoreboardDTO {

    private final List<Score> scores;

    /**
     * Constructor for ScoreboardDTO.
     * @param scoreboard the scoreboard
     */
    public ScoreboardDTO(final Scoreboard scoreboard) {
        this.scores = scoreboard.getScoreboard();
    }

    /**
     * Returns an unmodifiable List of Scores.
     * @return the scores
     */
    public List<Score> getScores() {
        return Collections.unmodifiableList(this.scores);
    }

    /**
     * Returns the top ten scores.
     * @return the top ten scores
     */
    public List<Score> getTopTenScores() {
        return this.getScores().stream()
            .limit(10)
            .sorted((s1, s2) -> Integer.compare(s2.getWave(), s1.getWave()))
            .collect(Collectors.toList());
    }
}
