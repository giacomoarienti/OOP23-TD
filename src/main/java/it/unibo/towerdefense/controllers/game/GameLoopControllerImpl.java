package it.unibo.towerdefense.controllers.game;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.controllers.defenses.DefensesController;
import it.unibo.towerdefense.controllers.enemies.EnemyControllerImpl;
import it.unibo.towerdefense.controllers.map.MapControllerImpl;
import it.unibo.towerdefense.models.game.GameLoop;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Game controller implementation.
 */
public class GameLoopControllerImpl implements GameLoopController {

    private final Logger logger;
    private final GameController gameController;
    private final GameRenderer gameRenderer;
    private final List<Controller> controllers;
    private boolean terminated;

    /**
     * Constructor with GameRender.
     * @param gameRenderer the game renderer
     */
    public GameLoopControllerImpl(final GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.logger = LoggerFactory.getLogger(this.getClass());
        // instantiate controllers
        this.gameController = new GameControllerImpl();
        // final DefensesController defensesController = new DefensesController(this.game);
        // final MapControllerImpl mapController = new MapControllerImpl(null, defensesController, gameController);
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
        this.gameController.save();
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
