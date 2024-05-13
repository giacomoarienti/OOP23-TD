package it.unibo.towerdefense.controllers.savings;

import it.unibo.towerdefense.models.savingloader.saving.Saving;

public interface SavingsController {

    /**
     * Starts a new game with the given saving.
     * @param saving the saving to load
     */
    void loadSaving(Saving saving);

    /**
     * Display the savings.
     */
    void displaySavings();
}
