package it.unibo.towerdefense.model.savingloader;

import java.util.List;

import it.unibo.towerdefense.model.savingloader.saving.Saving;

/**
 * Interface that defines the methods to load and save the game's saving.
 */
public interface SavingLoader {
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
