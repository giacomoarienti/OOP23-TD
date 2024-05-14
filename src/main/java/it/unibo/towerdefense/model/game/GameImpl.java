package it.unibo.towerdefense.model.game;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.game.GameDTOImpl;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Base implementation of the Game interface.
 */
public class GameImpl implements Game {

    private static final int START_LIVES = 100;
    private static final int START_MONEY = 500;
    private static final int START_WAVE = 1;
    private static final int PLAYING_GAME_SPEED = 1;
    private static final int PAUSE_GAME_SPEED = 0;

    private final List<Observer<GameDTO>> observers;
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
        // initialize empty list of observers
        this.observers = new ArrayList<>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.setGameState(GameState.PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.setGameState(GameState.PAUSE);
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
            this.notifyObservers();
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
        this.notifyObservers();
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
        this.notifyObservers();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPurchasable(final int amount) {
        return this.getMoney() >= amount;
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
        this.notifyObservers();
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
    public boolean isPlaying() {
        return this.getGameState().equals(GameState.PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameState(final GameState state) {
        this.gameState = state;
        this.notifyObservers();
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
    public void addObserver(final Observer<GameDTO> observer) {
        this.observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return this.toDTO().toJSON();
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

    private void notifyObservers() {
        this.observers.forEach(
            (obs) -> obs.notify(this.toDTO())
        );
    }
}
