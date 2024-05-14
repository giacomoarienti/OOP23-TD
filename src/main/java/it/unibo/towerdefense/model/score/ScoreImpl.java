package it.unibo.towerdefense.model.score;

import org.json.JSONObject;
import com.google.common.base.Objects;

/**
 * Implementation of the Score interface.
 */
public class ScoreImpl implements Score {

    private static final String NAME_FIELD = "name";
    private static final String WAVE_FIELD = "wave";
    private final String name;
    private final int wave;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put(NAME_FIELD, this.getName())
            .put(WAVE_FIELD, this.getWave())
            .toString();
    }

    /**
     * Returns the score object from JSON string.
     * @param jsonData the JSON representation of the scoreboard
     * @return the score object
     */
    public static Score fromJson(final String jsonData) {
        // convert the JSON string to a Score object
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new ScoreImpl(
            jsonObject.getString(NAME_FIELD),
            jsonObject.getInt(WAVE_FIELD)
        );
    }
}
