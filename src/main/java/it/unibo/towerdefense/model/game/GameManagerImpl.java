package it.unibo.towerdefense.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.game.GameDTOImpl;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.ModelManager;

/**
 * Base implementation of the Game interface.
 */
public class GameManagerImpl implements GameManager {

    private static final int START_LIVES = 100;
    private static final int START_MONEY = 500;
    private static final int START_WAVE = 1;
    private static final int PLAYING_GAME_SPEED = 1;
    private static final int PAUSE_GAME_SPEED = 0;
    private static final Logger logger =
        LoggerFactory.getLogger(GameManagerImpl.class);

    private final List<Observer<GameDTO>> observers;
    private final String playerName;
    private BindableConsumer<Integer> waveHandler;
    private GameStatus gameStatus;
    private boolean shouldWaveStart;
    private int lives;
    private int money;
    private int wave;

    /**
     * Constructor with playerName, it initializes a new game with default values.
     * @param playerName the player name
     */
    public GameManagerImpl(final String playerName) {
        this(playerName, START_LIVES, START_MONEY, START_WAVE);
    }

    /**
     * Constructor from GameDTO.
     * @param gameDTO the GameDTO object
     */
    public GameManagerImpl(final GameDTO gameDTO) {
        this(
            gameDTO.getPlayerName(),
            gameDTO.getLives(),
            gameDTO.getMoney(),
            gameDTO.getWave()
        );
    }

    private GameManagerImpl(
        final String playerName,
        final int lives,
        final int money,
        final int wave
    ) {
        this.playerName = playerName;
        this.lives = lives;
        this.money = money;
        this.wave = wave;
        this.gameStatus = GameStatus.PAUSE;
        // initialize empty list of observers
        this.observers = new ArrayList<>();
        // initialize waveHandler
        this.waveHandler = new BindableConsumer<>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.setGameStatus(GameStatus.PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.setGameStatus(GameStatus.PAUSE);
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
                this.notifyObservers();
                return true;
            }
        }
        // set game state to GAME_OVER and return false
        this.gameStatus = GameStatus.GAME_OVER;
        this.notifyObservers();
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
        this.shouldWaveStart = true;
        this.notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaying() {
        return this.getGameStatus().equals(GameStatus.PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameStatus(final GameStatus state) {
        this.gameStatus = state;
        this.notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGameSpeed() {
        return switch (this.gameStatus) {
            case PLAYING -> PLAYING_GAME_SPEED;
            case PAUSE -> PAUSE_GAME_SPEED;
            default -> throw new IllegalStateException("invalid gameState " + gameStatus.name());
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startWave() {
        this.shouldWaveStart = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO toDTO() {
        return new GameDTOImpl(
            this.playerName,
            this.lives,
            this.money,
            this.wave,
            this.gameStatus
        );
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
    public void bind(final ModelManager mm) {
        // bind waveHandler to spawn enemies
        this.waveHandler.bind(
            (wave) -> mm.getEnemies().spawn(wave)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (this.shouldWaveStart) {
            logger.info("Starting wave " + this.wave);
            this.waveHandler.accept(this.wave);
            this.shouldWaveStart = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.getGameStatus().equals(GameStatus.GAME_OVER);
    }

    private void notifyObservers() {
        this.observers.forEach(
            (obs) -> obs.notify(this.toDTO())
        );
    }

    /**
     * Class for a Consumer which can be defined after initialization.
     */
    private class BindableConsumer<T> implements Consumer<T> {
        private Optional<Consumer<T>> c;

        /**
         * Constructs the Consumer in a non-binded state.
         */
        private BindableConsumer() {
            c = Optional.empty();
        }

        /**
         * Constructs the Consumer in a non-binded state.
         * Calls to methods other than bind in this state will result in an
         * IllegalStateException.
         */
        @Override
        public void accept(final T t) {
            if (c.isPresent()) {
                c.get().accept(t);
            } else {
                throw new IllegalStateException("Consumer has not been binded yet.");
            }
        }

        /**
         * Binds the Consumer given as parameter as the one to get.
         * Must only be called once.
         * @param c the consumer to bind
         */
        private void bind(final Consumer<T> c) {
            if (this.c.isEmpty()) {
                this.c = Optional.of(c);
            } else {
                throw new IllegalStateException("Consumer has already been binded.");
            }
        }
    }
}
