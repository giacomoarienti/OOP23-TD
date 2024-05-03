package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.models.game.GameState;

/**
 * Interface that defines GameController methods.
 */
public interface GameController extends Controller {

    /**
     * Sets gameState to PLAYING.
     */
    void resume();

    /**
     * Sets gameState to PAUSE.
     */
    void pause();

    /**
     * Save the current game.
     * @return true if the game was saved
     */
    boolean save();

    /**
     * Increase wave number.
     */
    void advanceWave();

    /**
     * Gets the current wave number.
     * @return wave number
     */
    int getWave();

    /**
     * Get the money of the player.
     * @return the amount of money
     */
    int getMoney();

    /**
     * Increases the amount of money by amount .
     * @param amount quantity of money to be increased
     */
    void addMoney(int amount);

    /**
     * Decrease the player's money by amount .
     * @param amount quantity of money to be decreased
     * @return true if the operation was successful
     */
    boolean purchase(int amount);

    /**
     * Get lives of the player.
     * @return the amount of lives
     */
    int getLives();

    /**
     * Returns the current game state.
     * @return the game state
     */
    GameState getGameState();
}
