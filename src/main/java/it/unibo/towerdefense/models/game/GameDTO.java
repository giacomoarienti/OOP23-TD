package it.unibo.towerdefense.models.game;

import org.json.JSONObject;

import it.unibo.towerdefense.models.JsonSerializable;

/**
 * Game data transfer object.
 */
public class GameDTO implements JsonSerializable<GameDTO> {

    private final int lives;
    private final int money;
    private final int wave;

    /**
     * Constructor for the GameDTO.
     * @param lives the amount of lives
     * @param money the amount of money
     * @param wave the wave number
     */
    public GameDTO(final int lives, final int money, final int wave) {
        this.lives = lives;
        this.money = money;
        this.wave = wave;
    }

    private GameDTO() {
        this(0, 0, 0);
    }

    /**
     * Getter for the lives.
     * @return the amount of lives of the player
     */
    public final int getLives() {
        return this.lives;
    }

    /**
     * Getter for the money.
     * @return the amount of money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * Getter for the wave.
     * @return the wave number
     */
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
            .put("wave", this.getWave())
            .put("lives", this.getLives())
            .put("money", this.getMoney())
            .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO fromJSON(final String jsonData) {
        // convert the JSON string to a Game object
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new GameDTO(
            jsonObject.getInt("lives"),
            jsonObject.getInt("money"),
            jsonObject.getInt("wave"
        ));
    }

    /**
     * Returns the game dto object from JSON string.
     * @param jsonData the JSON representation of the game
     * @return the game dto
     */
    public static GameDTO fromJson(final String jsonData) {
        return new GameDTO().fromJSON(jsonData);
    }
}
