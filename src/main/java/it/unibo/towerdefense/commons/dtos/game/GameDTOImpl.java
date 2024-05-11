package it.unibo.towerdefense.commons.dtos.game;

import org.json.JSONObject;

import com.google.common.base.Objects;

/**
 * Game data transfer object.
 */
public class GameDTOImpl implements GameDTO {

    private static final String PLAYER_FIELD = "player";
    private static final String WAVE_FIELD = "wave";
    private static final String LIVES_FIELD = "lives";
    private static final String MONEY_FIELD = "money";
    private static final int DEFAULT_VALUE = 0;

    private final String playerName;
    private final int lives;
    private final int money;
    private final int wave;

    /**
     * Constructor for the GameDTO.
     * @param playerName the player name
     * @param lives the amount of lives
     * @param money the amount of money
     * @param wave the wave number
     */
    public GameDTOImpl(
        final String playerName,
        final int lives,
        final int money,
        final int wave
    ) {
        this.playerName = playerName;
        this.lives = lives;
        this.money = money;
        this.wave = wave;
    }

    /**
     * Constructor with playerName, it initializes a new game with default values.
     * @param playerName the player name
     */
    public GameDTOImpl(final String playerName) {
        this(
            playerName,
            DEFAULT_VALUE,
            DEFAULT_VALUE,
            DEFAULT_VALUE
        );
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
     * Getter for the playerName.
     * @return the player's name
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        // convert the Game object to a JSON string
       return new JSONObject()
            .put(PLAYER_FIELD, this.getPlayerName())
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
            jsonObject.getString(PLAYER_FIELD),
            jsonObject.getInt(LIVES_FIELD),
            jsonObject.getInt(MONEY_FIELD),
            jsonObject.getInt(WAVE_FIELD
        ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO copy() {
        return new GameDTOImpl(
            this.getPlayerName(),
            this.getLives(),
            this.getMoney(),
            this.getWave()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof GameDTOImpl) {
            final GameDTOImpl gameObject = (GameDTOImpl) obj;
            return this.getPlayerName().equals(gameObject.getPlayerName())
                && this.getLives() == gameObject.getLives()
                && this.getMoney() == gameObject.getMoney()
                && this.getWave() == gameObject.getWave();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.playerName, this.lives, this.money, this.wave);
    }
}
