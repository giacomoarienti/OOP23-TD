package it.unibo.towerdefense.commons.dtos.game;

import org.json.JSONObject;

import com.google.common.base.Objects;

import it.unibo.towerdefense.model.game.GameStatus;

/**
 * Game data transfer object.
 */
public class GameDTOImpl implements GameDTO {

    private static final String PLAYER_FIELD = "player";
    private static final String WAVE_FIELD = "wave";
    private static final String LIVES_FIELD = "lives";
    private static final String MONEY_FIELD = "money";
    private static final String STATUS_FIELD = "status";
    private static final int DEFAULT_VALUE = 0;
    private static final GameStatus DEFAULT_STATUS = GameStatus.PLAYING;

    private final String playerName;
    private final int lives;
    private final int money;
    private final int wave;
    private GameStatus status;

    /**
     * Constructor for the GameDTO.
     * @param playerName the player name
     * @param lives the amount of lives
     * @param money the amount of money
     * @param wave the wave number
     * @param status the game status
     */
    public GameDTOImpl(
        final String playerName,
        final int lives,
        final int money,
        final int wave,
        final GameStatus status

    ) {
        this.playerName = playerName;
        this.lives = lives;
        this.money = money;
        this.wave = wave;
        this.status = status;
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
            DEFAULT_VALUE,
            DEFAULT_STATUS
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getLives() {
        return this.lives;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoney() {
        return money;
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
    public String getPlayerName() {
        return playerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameStatus getStatus() {
        return status;
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
            .put(STATUS_FIELD, this.getStatus())
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
            jsonObject.getInt(WAVE_FIELD),
            jsonObject.getEnum(GameStatus.class, "status")
        );
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
            this.getWave(),
            this.getStatus()
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
                && this.getWave() == gameObject.getWave()
                && this.getStatus() == gameObject.getStatus();
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
