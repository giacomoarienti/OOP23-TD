package it.unibo.towerdefense.utils.savingloader;

import java.util.List;
import java.util.Optional;
import it.unibo.towerdefense.models.saving.Saving;

/**
 * Interface that defines the methods to load and save the game's saving.
 */
public interface SavingLoader {
    /**
     * Loads all saved games from user's local folder.
     * @return a list of saved games
     */
    Optional<List<Saving>> loadSavings();

    /**
     * Writes the game's saving to the user's local folder.
     * @param saving the Saving to be saved.
     * @return true if the saving has been saved, false otherwise.
     */
    boolean writeSaving(Saving saving);
}
