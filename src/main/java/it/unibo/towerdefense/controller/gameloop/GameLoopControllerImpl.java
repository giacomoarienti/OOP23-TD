package it.unibo.towerdefense.controller.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controller.mediator.ControllerMediator;
import it.unibo.towerdefense.model.gameloop.GameLoop;

/**
 * Game controller implementation.
 */
public class GameLoopControllerImpl implements GameLoopController {

    private final Logger logger;
    private final ControllerMediator masterController;
    private boolean terminated;

    /**
     * Constructor with GameRender.
     * @param masterController the master controller
     */
    public GameLoopControllerImpl(final ControllerMediator masterController) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.masterController = masterController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info("start()");
        // set the game state to playing
        this.masterController.getGameController().resume();
        // initialize game loop and start it
        final GameLoop.Builder gameLoopBuilder = new GameLoop.Builder();
        final GameLoop gameLoop = gameLoopBuilder.build(this);
        gameLoop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.masterController.getGameController().pause();
        this.terminated = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.masterController.getGameController().isPlaying();
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
        this.masterController.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.masterController.render();
    }
}
