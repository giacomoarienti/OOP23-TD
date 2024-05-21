package it.unibo.towerdefense.controller.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controller.Controller;

/**
 * GameLoop implementation.
 */
public class GameLoop implements Runnable {

    private static final String THREAD_NAME = "GameLoop";
    private static final int UPDATES_PER_SECOND = 60;
    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int UPDATE_RATE = MILLISECONDS_IN_SECOND / UPDATES_PER_SECOND;

    private final Logger logger;
    private final Controller controller;
    private long nextStatTime;
    private int fps, ups;

    /**
     * Constructor with GameController.
     * @param controller the game controller
     */
    public GameLoop(final Controller controller) {
        this.controller = controller;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.mainLoop();
    }

    /**
     * Start the game loop.
     */
    public void start() {
        new Thread(this, THREAD_NAME).start();
    }

    /**
     * Implementation inspired by {@link https://github.com/aricci303/game-as-a-lab/blob/master/Game-As-A-Lab-Step-5-component/src/rollball/core/GameEngine.java}.
     */
    private void mainLoop() {
        this.nextStatTime = System.currentTimeMillis() + (int) MILLISECONDS_IN_SECOND;
        long current = System.currentTimeMillis();
        // while game is running update state and render
        while (!this.controller.isTerminated()) {
            while (this.controller.isRunning()) {
                current = System.currentTimeMillis();
                this.update();
                this.render();
                this.printStats();
                this.waitForNextFrame(current);
            }
            // skip a frame if the game is paused
            this.waitForNextFrame(current + UPDATE_RATE);
            current = System.currentTimeMillis();
        }
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

    private void waitForNextFrame(final long current){
		final long dt = System.currentTimeMillis() - current;
		if (dt < UPDATE_RATE){
			try {
				Thread.sleep(UPDATE_RATE - dt);
			} catch (Exception ex) {
                logger.error("Error during waitForNextFrame()", ex);
            }
		}
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
        public final GameLoop build(final Controller controller) {
            if (this.consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            this.consumed = true;
            return new GameLoop(controller);
        }
    }
}
