package it.unibo.towerdefense.models.game;

import com.google.common.base.Objects;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.game.GameDTOImpl;

/**
 * Base implementation of the Game interface.
 */
public class GameImpl implements Game {

    private static final int START_LIVES = 100;
    private static final int START_MONEY = 500;
    private static final int START_WAVE = 1;
    private static final int PLAYING_GAME_SPEED = 1;
    private static final int PAUSE_GAME_SPEED = 0;

    private final String playerName;
    private int lives;
    private int money;
    private int wave;
    private GameState gameState;

    /**
     * Constructor with playerName, it initializes a new game with default values.
     * @param playerName the player name
     */
    public GameImpl(final String playerName) {
        this(playerName, START_LIVES, START_MONEY, START_WAVE);
    }

    /**
     * Constructor from GameDTO.
     * @param gameDTO the GameDTO object
     */
    public GameImpl(final GameDTO gameDTO) {
        this(
            gameDTO.getPlayerName(),
            gameDTO.getLives(),
            gameDTO.getMoney(),
            gameDTO.getWave()
        );
    }

    private GameImpl(
        final String playerName,
        final int lives,
        final int money,
        final int wave
    ) {
        this.playerName = playerName;
        this.lives = lives;
        this.money = money;
        this.wave = wave;
        this.gameState = GameState.PAUSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerName() {
        return this.playerName;
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
    public double getGameSpeed() {
        return switch (this.gameState) {
            case PLAYING -> PLAYING_GAME_SPEED;
            case PAUSE -> PAUSE_GAME_SPEED;
            default -> throw new IllegalStateException("invalid gameState " + gameState.name());
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO toDTO() {
        return new GameDTOImpl(this.playerName, this.lives, this.money, this.wave);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof GameImpl) {
            final GameImpl gameObject = (GameImpl) obj;
            return this.getPlayerName().equals(gameObject.getPlayerName())
                && this.getLives() == gameObject.getLives()
                && this.getMoney() == gameObject.getMoney()
                && this.getWave() == gameObject.getWave()
                && this.getGameState() == gameObject.getGameState();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(
            this.playerName,
            this.lives,
            this.money,
            this.wave,
            this.gameState
        );
    }
}
