package it.unibo.towerdefense.models.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.models.ObservableModel;

/**
 * Model containing base game's statistics and info.
 */
public interface Game extends ObservableModel<GameDTO> {

    /**
     * Player name getter.
     * @return the player's name
     */
    String getPlayerName();

    /**
     * Lives getter.
     * @return the amount of lives of the player
     */
    int getLives();

    /**
     * Decrease by 1 the number of lives of the player.
     * @return true if number of lives is greater then 0
     */
    boolean decreaseLives();

    /**
     * Money getter.
     * @return the amount of money the player has
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
     * Wave getter.
     * @return the wave number
     */
    int getWave();

    /**
     * Advances to the next wave.
     */
    void advanceWave();

    /**
     * GameState getter.
     * @return the state of the game
     */
    GameState getGameState();

    /**
     * Update the current game state.
     * @param state the new game state
     */
    void setGameState(GameState state);

    /**
     * Getter game speed.
     * @return current game speed
     */
    double getGameSpeed();

    /**
     * Returns the DTO representation of the Game object.
     * @return the GameDTO object
     */
    GameDTO toDTO();

    /**
     * Returns the Game object from a GameDTO object.
     * @param dto the GameDTO object
     * @return the Game object
     */
    static Game fromDTO(final GameDTO dto) {
        return new GameImpl(dto);
    }
}
