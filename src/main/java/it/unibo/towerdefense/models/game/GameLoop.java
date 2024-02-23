package it.unibo.towerdefense.models.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.game.GameController;

/**
 * GameLoop implementation.
 */
public class GameLoop implements Runnable {

    private static final int UPDATES_PER_SECOND = 60;
    private static final double UPDATE_RATE = 1.0d / UPDATES_PER_SECOND;
    private static final double MILLISECONDS_IN_SECOND = 1000d;
    private static final int ROUNDING_DELTA = 0;

    private final Logger logger;
    private final GameController controller;
    private long nextStatTime;
    private int fps, ups;

    /**
     * Constructor with GameController.
     * @param controller the game controller
     */
    public GameLoop(final GameController controller) {
        this.controller = controller;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        this.nextStatTime = System.currentTimeMillis() + (int) MILLISECONDS_IN_SECOND;
        // while game is running update state and render
        while (this.controller.isRunning()) {
            currentTime = System.currentTimeMillis();
            final double lastRenderTime = (currentTime - lastUpdate) / MILLISECONDS_IN_SECOND;
            accumulator += lastRenderTime * this.controller.getGameSpeed();
            lastUpdate = currentTime;
            /*
             * render only if we do not exceed the UPDATE_RATE,
             * prevent rounding problems using int comparison
             */
            while (accumulator - UPDATE_RATE > ROUNDING_DELTA) {
                this.update();
                this.render();
                accumulator -= UPDATE_RATE;
            }
            // print statistics for debug purposes
            this.printStats();
        }
    }

    private void printStats() {
        if (System.currentTimeMillis() > this.nextStatTime) {
            logger.debug(String.format("FPS: %d, UPS: %d", this.fps, this.ups));
            this.fps = 0;
            this.ups = 0;
            this.nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() {
        // TODO: update
        this.ups++;
    }

    private void render() {
        // TODO: render
        this.fps++;
    }
}
