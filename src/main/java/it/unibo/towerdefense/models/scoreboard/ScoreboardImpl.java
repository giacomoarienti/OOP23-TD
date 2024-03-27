package it.unibo.towerdefense.models.scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.models.scoreboard.score.Score;
import it.unibo.towerdefense.models.scoreboard.score.ScoreImpl;
import it.unibo.towerdefense.utils.file.FileUtils;

/**
 * Class implementing the Scoreboard interface.
 */
public class ScoreboardImpl implements Scoreboard {

    private static final String SCOREBOARD_PATH = Constants.GAME_FOLDER
            + File.separator
            + "scoreboard.json";

    private final Set<Score> scores = new TreeSet<>();
    private final Logger logger;

    /**
     * Zero-argument constructor.
     * @throws IOException if the scoreboard file cannot be created
     */
    public ScoreboardImpl() throws IOException {
        // create the file and load scores
        FileUtils.createFile(SCOREBOARD_PATH);
        // create the logger
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Score> getScoreboard() {
        return Collections.unmodifiableSet(this.scores);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadScores() {
        try {
            final String jsonData = FileUtils.readFile(SCOREBOARD_PATH);
            this.scores.clear();
            // convert the JSON string to a set of Score objects
            final JSONArray jsonArray = new JSONArray(jsonData);
            for (final Object object : jsonArray) {
                final JSONObject jsonObject = (JSONObject) object;
                this.scores.add(Score.fromJson(jsonObject.toString()));
            }
        } catch (final IOException e) {
            this.scores.clear();
            logger.error("Failed to load scores", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveScore(final String name, final int wave) {
        // create the score object
        final Score score = new ScoreImpl(name, wave);
        try {
            final String jsonData = FileUtils.readFile(SCOREBOARD_PATH);
            // convert the JSON string to a set of Score objects
            final JSONArray jsonArray = new JSONArray(jsonData);
            jsonArray.put(new JSONObject(score.toJSON()));
            // save the json string to file
            FileUtils.writeFile(SCOREBOARD_PATH, jsonArray.toString());
        } catch (final IOException e) {
            logger.error("Failed to save score", e);
            return false;
        }
        // score saved
        return true;
    }
}
