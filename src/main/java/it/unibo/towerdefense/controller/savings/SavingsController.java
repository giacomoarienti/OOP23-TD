package it.unibo.towerdefense.controller.savings;

import it.unibo.towerdefense.model.savingloader.saving.Saving;

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
