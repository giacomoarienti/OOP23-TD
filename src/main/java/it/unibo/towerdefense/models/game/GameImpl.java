package it.unibo.towerdefense.models.game;

/**
 * Base implementation of the Game interface.
 */
public class GameImpl implements Game {

    private static final int START_LIVES = 100;
    private static final int START_MONEY = 500;
    private static final int START_WAVE = 1;
    private static final int START_SCORE = 0;

    private int lives;
    private int money;
    private int wave;
    private int score;
    private GameState gameState;

    /**
     * Zero-argument constructor, set default values.
     */
    public GameImpl() {
        this.lives = START_LIVES;
        this.money = START_MONEY;
        this.wave = START_WAVE;
        this.score = START_SCORE;
        this.gameState = GameState.PLAYING;
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
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addScore(final int points) {
        // check if points its positive
        if (points <= 0) {
            throw new IllegalArgumentException("points must be positive");
        }
        this.score += points;
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
    public double getGameSpeed() {
        return switch (this.gameState) {
            case PLAYING -> 1;
            case FAST_FORWARDING -> 2;
            case PAUSE -> 0;
            default -> throw new IllegalStateException("invalid gameState " + gameState.name());
        };
    }

}
