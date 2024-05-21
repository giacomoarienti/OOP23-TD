package it.unibo.towerdefense.model.game;

import it.unibo.towerdefense.commons.dtos.game.ControlAction;

/**
 * Current game state.
 */
public enum GameStatus {
    /**
     * Game is running.
     */
    PLAYING,

    /**
     * Game is in pause.
     */
    PAUSE,

    /**
     * Game is over.
     */
    GAME_OVER;

    /**
     * Convert a control action to a game status.
     * @param action the control action
     * @return the game status
     */
    public static GameStatus fromControlAction(final ControlAction action) {
        switch (action) {
            case PAUSE:
            case QUIT:
                return PAUSE;
            case RESUME:
                return PLAYING;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }
}
