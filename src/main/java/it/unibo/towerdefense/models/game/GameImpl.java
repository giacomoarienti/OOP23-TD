package it.unibo.towerdefense.models.game;

import org.json.JSONObject;

/**
 * Base implementation of the Game interface.
 */
public class GameImpl implements Game {

    private static final int START_LIVES = 100;
    private static final int START_MONEY = 500;
    private static final int START_WAVE = 1;
    private static final int PLAYING_GAME_SPEED = 1;
    private static final int FAST_FORWARD_GAME_SPEED = 2;
    private static final int PAUSE_GAME_SPEED = 0;

    private int lives;
    private int money;
    private int wave;
    private GameState gameState;

    /**
     * Zero-argument constructor, set default values.
     */
    public GameImpl() {
        this.lives = START_LIVES;
        this.money = START_MONEY;
        this.wave = START_WAVE;
        this.gameState = GameState.PLAYING;
    }

    /**
     * Constructors a GameImpl with the given lives, money and wave.
     * @param lives the amount of lives
     * @param money the amount of money
     * @param wave the wave number
     */
    public GameImpl(final int lives, final int money, final int wave) {
        this.lives = lives;
        this.money = money;
        this.wave = wave;
        this.gameState = GameState.PAUSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLives() {
        return this.lives;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean decreaseLives() {
        // if lives is positive decrease them
        if (this.lives > 0) {
            this.lives -= 1;
            // return true if positive
            if (this.lives > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoney() {
        return this.money;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMoney(final int amount) {
        // check if money is positive
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.money += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean purchase(final int amount) {
        // check if money is positive
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        // return false if is not possible to purchase
        if (this.money < amount) {
            return false;
        }
        // decrease amount and return true
        this.money -= amount;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWave() {
        return this.wave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceWave() {
        this.wave++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameState(final GameState state) {
        this.gameState = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        final GameState gameState = this.getGameState();
        return gameState.equals(GameState.PLAYING)
            || gameState.equals(GameState.FAST_FORWARDING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGameSpeed() {
        return switch (this.gameState) {
            case PLAYING -> PLAYING_GAME_SPEED;
            case FAST_FORWARDING -> FAST_FORWARD_GAME_SPEED;
            case PAUSE -> PAUSE_GAME_SPEED;
            default -> throw new IllegalStateException("invalid gameState " + gameState.name());
        };
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
    public Game fromJSON(final String jsonData) {
        // convert the JSON string to a Game object
        final JSONObject jsonObject = new JSONObject(jsonData);
        final int money = jsonObject.getInt("money");
        final int lives = jsonObject.getInt("lives");
        final int wave = jsonObject.getInt("wave");
        final Game game = new GameImpl(lives, money, wave);
        return game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game copy() {
        return new GameImpl(this.lives, this.money, this.wave);
    }
}
