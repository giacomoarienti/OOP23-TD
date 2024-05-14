package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.controllers.SerializableController;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.utils.patterns.Observer;

/**
 * Interface that defines GameController methods.
 */
public interface GameController extends SerializableModel {

    /**
     * Sets gameState to PLAYING.
     */
    void resume();

    /**
     * Sets gameState to PAUSE.
     */
    void pause();

    /**
     * Increase wave number.
     */
    void advanceWave();

    /**
     * Gets the player's name.
     * @return the player's name
     */
    String getPlayerName();

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
     * Check if the player has enough money to buy something.
     * @param amount the amount of money to be checked
     * @return true if the player has enough money
     */
    boolean isPurchasable(int amount);

    /**
     * Check if the game is playing.
     * @return true if the game is RUNNING
     */
    boolean isPlaying();

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

    /**
     * Add observer to the Game Model.
     * @param observer the observer to be added
     */
    void addObserver(Observer<GameDTO> observer);
}
