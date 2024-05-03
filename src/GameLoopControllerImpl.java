package it.unibo.towerdefense.controllers.game;

import java.util.List;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.controllers.SerializableController;
import it.unibo.towerdefense.models.game.GameLoop;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.models.savingloader.SavingLoaderImpl;
import it.unibo.towerdefense.models.savingloader.saving.SavingImpl;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Game controller implementation.
 */
public class GameLoopControllerImpl implements GameLoopController {

    private final Logger logger;
    private final GameRenderer gameRenderer;
    private final List<Controller> controllers;
    private final GameController gameController;
    private final SerializableController mapController;
    private final SerializableController defensesController;
    private boolean terminated;

    /**
     * Constructor with GameRender.
     * @param playerName the player name
     * @param gameRenderer the game renderer
     */
    public GameLoopControllerImpl(final String playerName, final GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.logger = LoggerFactory.getLogger(this.getClass());
        // instantiate controllers
        this.gameController = new GameControllerImpl(playerName);
        this.defensesController = null; //new DefensesControllerImpl(this.game);
        this.mapController = null; //new MapControllerImpl(null, defensesController, gameController);
        // final EnemyControllerImpl enemyController = new EnemyControllerImpl(mapController, gameController);
        this.controllers = List.of(
            // mapController,
            gameController//,
            // defensesController,
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
        this.gameController.resume();
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
        this.gameController.pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.gameController.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.gameController.pause();
        this.terminated = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        // create saving instance
        final SavingImpl saving = new SavingImpl(
            this.gameController.toJSON(),
            this.mapController.toJSON(),
            this.defensesController.toJSON()
        );
        // write saving
        try {
            final SavingLoaderImpl savingLoader = new SavingLoaderImpl(
                this.gameController.getPlayerName()
            );

            if(!savingLoader.writeSaving(saving)) {
                // TODO handle error
                logger.error("Error saving game");
            }
        } catch (final IOException e) {
            // TODO handle error
            logger.error("Error saving game");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.gameController.getGameState()
            .equals(GameState.PLAYING);
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
