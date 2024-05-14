package it.unibo.towerdefense.controller.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controller.Controller;

/**
 * Game controller implementation.
 */
public class GameLoopControllerImpl implements GameLoopController {

    private final Logger logger;
    private final Controller controller;
    private boolean terminated;

    /**
     * Constructor with GameRender.
     * @param manager the master controller
     */
    public GameLoopControllerImpl(final Controller controller) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info("start()");
        // set the game state to playing
        this.controller.resume();
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
        this.controller.stop();
        this.terminated = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.controller.isPlaying();
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
        this.controller.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.controller.render();
    }
}
