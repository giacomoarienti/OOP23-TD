package it.unibo.towerdefense.controllers.savings;

import java.util.List;

import it.unibo.towerdefense.models.savingloader.saving.Saving;

public interface SavingsController {

    /**
     * Starts a new game with the given saving.
     * @param saving the saving to load
     */
    void loadSaving(Saving saving);

    /**
     * Get the list of the savings.
     * @return the list of the savings
     */
    List<Saving> getSavings();
}
