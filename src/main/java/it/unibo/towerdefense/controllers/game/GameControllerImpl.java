package it.unibo.towerdefense.controllers.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.controllers.defenses.DefensesController;
import it.unibo.towerdefense.controllers.enemies.EnemyControllerImpl;
import it.unibo.towerdefense.controllers.map.MapControllerImpl;
import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameLoop;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Game controller implementation.
 */
public class GameControllerImpl implements GameController {

    private final Logger logger;
    private final Game game;
    private final GameRenderer gameRenderer;
    private final List<Controller> controllers;
    private boolean terminated;

    /**
     * Constructor with GameRender.
     * @param gameRenderer the game renderer
     */
    public GameControllerImpl(final GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.game = new GameImpl();
        // instantiate controllers
        // final DefensesController defensesController = new DefensesController(this.game);
        // final MapControllerImpl mapController = new MapControllerImpl(null, defensesController, this);
        // final EnemyControllerImpl enemyController = new EnemyControllerImpl(null, mapController, this);
        this.controllers = List.of(
            // defensesController,
            // mapController,
            // enemyController
        );
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
        /*// create saving instance
        final SavingImpl saving = new SavingImpl();
        // write saving
        try {
            final SavingLoader savingLoader = new SavingLoaderImpl();
            savingLoader.writeSaving(saving);
            return true;
        } catch (final IOException e) {
            logger.error("Error saving game", e);
            return false;
        }*/
        return true;
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
            controller -> controller.render(gameRenderer)
        );
        // force repaint
        this.gameRenderer.renderCanvas();
    }
}
