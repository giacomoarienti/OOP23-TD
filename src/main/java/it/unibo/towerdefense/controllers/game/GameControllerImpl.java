package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameState;

/**
 * Game controller
 */
public class GameControllerImpl  implements GameController {

    private final Game game;

    /**
     * Zero-argument constructor
     */
    public GameControllerImpl() {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }
}
