package it.unibo.towerdefense.model.saves;

import java.util.List;

/**
 * Interface that defines the methods to load and save the game's save.
 */
public interface Saves {

    /**
     * Loads all saved games from user's local folder.
     * @return a list of saved games
     */
    List<Save> loadSaves();

    /**
     * Writes the game's save to the user's local folder.
     * @param save the Save to be saved.
     * @return true if the save has been saved, false otherwise.
     */
    boolean writeSave(Save save);
}
