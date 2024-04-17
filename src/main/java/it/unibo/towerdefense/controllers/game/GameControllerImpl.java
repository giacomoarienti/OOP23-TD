package it.unibo.towerdefense.controllers.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.controllers.menu.MenuController;
import it.unibo.towerdefense.controllers.menu.MenuControllerImpl;
import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameLoop;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.models.savingloader.SavingLoader;
import it.unibo.towerdefense.models.savingloader.SavingLoaderImpl;
import it.unibo.towerdefense.models.savingloader.saving.SavingImpl;
import it.unibo.towerdefense.views.window.Window;

/**
 * Game controller implementation.
 */
public class GameControllerImpl  implements GameController {

    private final Logger logger;
    private final Game game;
    private final MenuController menuController;
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
        this.menuController = new MenuControllerImpl(this, this.window);
        this.controllers = new ArrayList<>();
        // TODO add controllers
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        logger.info("run()");
        // display the window
        this.window.display();
        // display the StartMenu
        this.menuController.displayStartMenu();
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
    public void exit() {
        logger.info("exit()");
        // save the current game state
        this.saveGame();
        // terminate the game
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

    private void saveGame() {
        try {
            final SavingLoader savingLoader = new SavingLoaderImpl();
            final SavingImpl saving = new SavingImpl();
            savingLoader.writeSaving(saving);
        } catch (final IOException e) {
            logger.error("Error saving game", e);
            this.window.displayError("Error saving game");
        }
    }
}
