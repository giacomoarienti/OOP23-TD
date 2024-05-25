package it.unibo.towerdefense.controller.savings;

import java.util.List;

import it.unibo.towerdefense.model.saving.Saving;

/**
 * Interface that models the controller of the savings.
 */
public interface SavingsController {

    /**
     * Runs the controller and display the SavingView.
     */
    void run();

    /**
     * Returns the savings to display.
     * @return the list of savings to display
     */
    List<Saving> getSavings();

    /**
     * Starts a new game with the given saving.
     * @param saving the saving to load
     */
    void loadSaving(Saving saving);
}
