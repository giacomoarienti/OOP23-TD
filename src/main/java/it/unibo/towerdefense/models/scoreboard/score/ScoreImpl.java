package it.unibo.towerdefense.models.scoreboard.score;

import org.json.JSONObject;
import com.google.common.base.Objects;

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

    /**
     * Constructor from name and wave.
     * @param name the player's name
     * @param wave the reached wave
     */
    public ScoreImpl(final String name, final int wave) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Score o) {
        return Integer.compare(o.getWave(), this.getWave());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ScoreImpl) {
            final Score scoreObject = (ScoreImpl) o;
            return this.getWave() == scoreObject.getWave()
                && this.getName().equals(scoreObject.getName());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName(), this.getWave());
    }

}
