package it.unibo.towerdefense.view.savings;

import java.util.List;

import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.view.modal.ModalContent;

/**
 * Savings View interface.
 * Display the list of the savings and allow the user to load them.
 */
public interface SavingsView extends ModalContent {

    /**
     * Set the list of the savings.
     * @param savings the list of the savings
     */
    void setSavings(List<Saving> savings);
}
