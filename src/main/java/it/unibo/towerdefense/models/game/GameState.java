package it.unibo.towerdefense.models.game;

/**
 * Current game state.
 */
public enum GameState {
    /**
     * Game is running.
     */
    PLAYING,

    /**
     * Game is fast forwarding.
     */
    FAST_FORWARDING,

    /**
     * Game is in pause.
     */
    PAUSE
}
