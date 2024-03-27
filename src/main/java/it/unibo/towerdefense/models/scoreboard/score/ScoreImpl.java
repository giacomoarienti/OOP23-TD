package it.unibo.towerdefense.models.scoreboard.score;

import org.json.JSONObject;

/**
 * Implementation of the Score interface.
 */
public class ScoreImpl implements Score {

    private final String name;
    private final int wave;

    /**
     * Zero-argument constructor.
     */
    public ScoreImpl() {
        this.name = "";
        this.wave = 0;
    }

    private ScoreImpl(final String name, final int wave) {
        this.name = name;
        this.wave = wave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWave() {
        return wave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Score fromJSON(final String jsonData) {
        // convert the JSON string to a Score object
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new ScoreImpl(
            jsonObject.getString("name"),
            jsonObject.getInt("wave")
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put("name", this.getName())
            .put("wave", this.getWave())
            .toString();
    }

}
