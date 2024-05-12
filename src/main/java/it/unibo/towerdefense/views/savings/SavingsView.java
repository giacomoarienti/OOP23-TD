package it.unibo.towerdefense.views.savings;

import java.util.List;

import it.unibo.towerdefense.models.savingloader.saving.Saving;
import it.unibo.towerdefense.views.modal.ModalContent;

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
