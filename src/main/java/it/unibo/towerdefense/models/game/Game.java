package it.unibo.towerdefense.models.game;

/**
 * Model containing base game's statistics and info.
 */
public interface Game {

    /**
     * Lives getter.
     * @return the amount of lives of the player
     */
    int getLives();

    /**
     * Decrease by 1 the number of lives of the player.
     * @return true if number of lives is greather then 0
     */
    boolean decreaseLives();

    /**
     * Money getter.
     * @return the amount of money the player has
     */
    int getMoney();

    /**
     * Increases the amount of money by @param amount .
     * @param amount quantity of money to be increased
     */
    void addMoney(int amount);

    /**
     * Decrease the player's money by @param amount .
     * @param amount quantity of money to be decreased
     * @return true if the operation was successfull
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
     * Score getter.
     * @return the score of the player
     */
    int getScore();

    /**
     * Increase the score by @param points .
     * @param points amount of points to be added to the score
     */
    void addScore(int points);

    /**
     * GameState getter.
     * @return the steate of the game
     */
    GameState getGameState();

    /**
     * Update the current game state.
     * @param state the new game state
     */
    void setGameState(GameState state);
}
