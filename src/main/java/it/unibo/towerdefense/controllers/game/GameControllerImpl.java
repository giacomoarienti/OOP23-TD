package it.unibo.towerdefense.controllers.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameState;

/**
 * Game controller implementation.
 */
public class GameControllerImpl  implements GameController {

    private final Logger logger;
    private final Game game;

    /**
     * Zero-argument constructor.
     */
    public GameControllerImpl() {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.game = new GameImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseGame() {
        this.game.setGameState(GameState.PAUSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeGame() {
        this.game.setGameState(GameState.PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        // TODO: exit
        logger.info("exit()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGameSpeed() {
        return game.getGameSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameSelection() {
        // TODO: implement game selection
        throw new UnsupportedOperationException("Unimplemented method 'gameSelection'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.game.isRunning();
    }
}
