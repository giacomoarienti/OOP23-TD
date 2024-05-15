package it.unibo.towerdefense.model;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.saving.Saving;

public interface Model {

    /**
     * Initialize the model.
     * @param playerName the player name
     * @param mapSize the size of the map
     */
    void init(String playerName, Size mapSize);

    /**
     * Initialize the model from a saving object.
     * @param saving the saving object
     */
    void init(Saving saving);

    /**
     * Checks if the game is playing.
     * @return true if the game is playing
     */
    boolean isPlaying();

    /**
     * Resume the game.
     */
    void resume();

    /**
     * Adds an observer to the game model.
     * @param observer the observer to add
     */
    void addGameObserver(Observer<GameDTO> observer);
}
