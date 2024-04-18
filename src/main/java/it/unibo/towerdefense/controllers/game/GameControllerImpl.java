package it.unibo.towerdefense.controllers.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameLoop;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.models.savingloader.SavingLoader;
import it.unibo.towerdefense.models.savingloader.SavingLoaderImpl;
import it.unibo.towerdefense.models.savingloader.saving.SavingImpl;

/**
 * Game controller implementation.
 */
public class GameControllerImpl implements GameController {

    private final Logger logger;
    private final Game game;
    private final List<Controller> controllers;
    private boolean terminated;

    /**
     * Constructor with Window.
     */
    public GameControllerImpl() {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.game = new GameImpl();
        // instantiate controllers
        this.controllers = new ArrayList<>();
        // TODO add controllers
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info("start()");
        // set the game state to playing
        this.game.setGameState(GameState.PLAYING);
        // initialize game loop and start it
        final GameLoop.Builder gameLoopBuilder = new GameLoop.Builder();
        final GameLoop gameLoop = gameLoopBuilder.build(this);
        gameLoop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.game.setGameState(GameState.PAUSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.game.setGameState(GameState.PLAYING);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save() {
        // TODO pass the game, map and defenses to the SavingImpl constructor
        // create saving instance
        final SavingImpl saving = new SavingImpl();
        // write saving
        try {
            final SavingLoader savingLoader = new SavingLoaderImpl();
            savingLoader.writeSaving(saving);
            return true;
        } catch (final IOException e) {
            logger.error("Error saving game", e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceWave() {
        this.game.advanceWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGameSpeed() {
        return this.game.getGameSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWave() {
        return this.game.getWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoney() {
        return this.game.getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.game.isRunning();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTerminated() {
        return this.terminated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        // calls update on each controller
        this.controllers.stream()
        .forEach(
            controller -> controller.update()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        // calls render on each controller
        this.controllers.stream()
        .forEach(
            controller -> controller.render()
        );
    }
}
