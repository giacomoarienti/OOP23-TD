package it.unibo.towerdefense.model.saving;

import java.util.List;

/**
 * Interface that defines the methods to load and save the game's saving.
 */
public interface Savings {

    /**
     * Loads all saved games from user's local folder.
     * @return a list of saved games
     */
    List<Saving> loadSavings();

    /**
     * Writes the game's saving to the user's local folder.
     * @param saving the Saving to be saved.
     * @return true if the saving has been saved, false otherwise.
     */
    boolean writeSaving(Saving saving);
}
