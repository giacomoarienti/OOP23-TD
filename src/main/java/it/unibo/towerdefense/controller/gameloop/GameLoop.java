package it.unibo.towerdefense.controller.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GameLoop implementation.
 */
public class GameLoop implements Runnable {

    private static final String THREAD_NAME = "GameLoop";
    private static final int UPDATES_PER_SECOND = 60;
    private static final double UPDATE_RATE = 1.0d / UPDATES_PER_SECOND;
    private static final double MILLISECONDS_IN_SECOND = 1000d;
    private static final int ROUNDING_DELTA = 0;

    private final Logger logger;
    private final GameLoopController controller;
    private long nextStatTime;
    private int fps, ups;

    /**
     * Constructor with GameController.
     * @param controller the game controller
     */
    public GameLoop(final GameLoopController controller) {
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
        while (!this.controller.isTerminated()) {
            while (this.controller.isRunning()) {
                currentTime = System.currentTimeMillis();
                final double lastRenderTime = (currentTime - lastUpdate) / MILLISECONDS_IN_SECOND;
                accumulator += lastRenderTime;
                lastUpdate = currentTime;
                /* render only if we do not exceed the UPDATE_RATE,
                prevent rounding problems using int comparison */
                while (accumulator - UPDATE_RATE > ROUNDING_DELTA) {
                    this.update();
                    this.render();
                    accumulator -= UPDATE_RATE;
                }
                // print statistics for debug purposes
                this.printStats();
            }
            // if the game loop should not update, skip a frame
            try {
                Thread.sleep((int) MILLISECONDS_IN_SECOND / UPDATES_PER_SECOND);
            } catch (final InterruptedException e) {
                logger.error("Error in game loop", e);
            }
        }
    }

    /**
     * Start the game loop.
     */
    public void start() {
        new Thread(this, THREAD_NAME).start();
    }

    private void printStats() {
        if (System.currentTimeMillis() > this.nextStatTime) {
            logger.debug(String.format("FPS: %d, UPS: %d", this.fps, this.ups));
            this.fps = 0;
            this.ups = 0;
            this.nextStatTime = System.currentTimeMillis() + (int) MILLISECONDS_IN_SECOND;
        }
    }

    private void update() {
        this.ups++;
        this.controller.update();
    }

    private void render() {
        this.fps++;
        this.controller.render();
    }

    /**
     * GameLoop builder.
     */
    public static class Builder {

        private boolean consumed;

        /**
         * Build the GameLoop.
         * @param controller the game controller
         * @return the GameLoop instance.
         */
        public final GameLoop build(final GameLoopController controller) {
            if (this.consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            this.consumed = true;
            return new GameLoop(controller);
        }
    }
}
