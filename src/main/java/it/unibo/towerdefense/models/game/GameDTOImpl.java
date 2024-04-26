package it.unibo.towerdefense.models.game;

import org.json.JSONObject;

/**
 * Game data transfer object.
 */
public class GameDTOImpl implements GameDTO {

    private static final String WAVE_FIELD = "wave";
    private static final String LIVES_FIELD = "lives";
    private static final String MONEY_FIELD = "money";

    private final int lives;
    private final int money;
    private final int wave;

    /**
     * Constructor for the GameDTO.
     * @param lives the amount of lives
     * @param money the amount of money
     * @param wave the wave number
     */
    public GameDTOImpl(final int lives, final int money, final int wave) {
        this.lives = lives;
        this.money = money;
        this.wave = wave;
    }

    /**
     * Getter for the lives.
     * @return the amount of lives of the player
     */
    @Override
    public final int getLives() {
        return this.lives;
    }

    /**
     * Getter for the money.
     * @return the amount of money the player has
     */
    @Override
    public int getMoney() {
        return money;
    }

    /**
     * Getter for the wave.
     * @return the wave number
     */
    @Override
    public int getWave() {
        return wave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        // convert the Game object to a JSON string
       return new JSONObject()
            .put(WAVE_FIELD, this.getWave())
            .put(LIVES_FIELD, this.getLives())
            .put(MONEY_FIELD, this.getMoney())
            .toString();
    }

    /**
     * Returns the GameDTO object from JSON string.
     * @param jsonData the JSON representation
     * @return the GameDTO object
     */
    public static GameDTO fromJson(final String jsonData) {
        // convert the JSON string to a Game object
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new GameDTOImpl(
            jsonObject.getInt(LIVES_FIELD),
            jsonObject.getInt(MONEY_FIELD),
            jsonObject.getInt(WAVE_FIELD
        ));
    }
}
