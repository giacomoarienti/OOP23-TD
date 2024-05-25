package it.unibo.towerdefense.model.scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.model.score.Score;
import it.unibo.towerdefense.model.score.ScoreImpl;

/**
 * Class implementing the Scoreboard interface.
 */
public class ScoreboardImpl implements Scoreboard {

    private static final String MESSAGE_ERROR = "Scoreboard file is corrupted!";
    private static final String SCOREBOARD_PATH = Constants.GAME_FOLDER
            + File.separator
            + "scoreboard.json";

    private final List<Score> scores = new ArrayList<>();
    private final String filePath;
    private final Logger logger =
        LoggerFactory.getLogger(ScoreboardImpl.class);

    /**
     * Constructor with file path.
     * @param path the path of the scoreboard file
     * @throws IOException if the scoreboard file cannot be created
     */
    public ScoreboardImpl(final String path) throws IOException {
        this.filePath = path;
        // create the file in case it does not exist
        FileUtils.createFile(filePath);
    }

    /**
     * Zero-argument constructor.
     * @throws IOException if the scoreboard file cannot be created
     */
    public ScoreboardImpl() throws IOException {
        this(SCOREBOARD_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Score> getScoreboard() {
        return Collections.unmodifiableList(this.scores);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadScores() throws IOException {
        try {
            // read the JSON string from file
            final String jsonData = FileUtils.readFile(filePath);
            this.scores.clear();
            // if the file is empty return
            if (jsonData.isEmpty()) {
                return;
            }
            // convert the JSON string to a set of Score objects
            final JSONArray jsonArray = new JSONArray(jsonData);
            for (final Object object : jsonArray) {
                final JSONObject jsonObject = (JSONObject) object;
                this.scores.add(Score.fromJson(jsonObject.toString()));
            }
        } catch (final JSONException e) {
            this.scores.clear();
            logger.error(MESSAGE_ERROR);
            throw new IOException(MESSAGE_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Score saveScore(final String name, final int wave) throws IOException {
        // load the current scores
        this.loadScores();
        // add the new score
        final var score = new ScoreImpl(name, wave);
        this.scores.add(score);
        try {
            // add each score to the a JSON array
            final JSONArray jsonArray =  new JSONArray();
            this.scores.forEach((s) ->
                jsonArray.put(new JSONObject(s.toJSON()))
            );
            // save the json string to file
            FileUtils.writeFile(filePath, jsonArray.toString());
        } catch (final JSONException e) {
            logger.error(MESSAGE_ERROR);
            throw new IOException(MESSAGE_ERROR, e);
        }
        // score saved
        return score;
    }
}
