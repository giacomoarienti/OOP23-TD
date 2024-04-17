package it.unibo.towerdefense.controllers.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.window.Window;

/**
 * Game controller implementation.
 */
public class GameControllerImpl  implements GameController {

    private final Logger logger;
    private final Game game;
    private final List<Controller> controllers;
    private final Window window;
    private boolean terminated;

    /**
     * Constructor with Window.
     * @param window the interface's window
     */
    public GameControllerImpl(final Window window) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.window = window;
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
        // TODO to be implemented
        logger.info("start()");
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
    public void exit() {
        logger.info("exit()");
        this.terminated = true;
        this.window.close();
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
