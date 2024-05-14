package it.unibo.towerdefense.controller.savings;

import java.io.IOException;

import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.view.savings.SavingsView;
import it.unibo.towerdefense.view.savings.SavingsViewImpl;

/**
 * Implementation of SavingsController.
 */
public class SavingsControllerImpl implements SavingsController {

    private final Controller controller;
    private final SavingsView savingsView;

    /**
     * Loads savings from the SavingLoader.
     * @param playerName the name of the player
     * @param controller the application controller
     */
    public SavingsControllerImpl(final String playerName, final Controller controller) {
        this.controller = controller;
        this.savingsView = new SavingsViewImpl(this);
        // load savings
        try {
            final var savingLoader = new SavingLoaderImpl(playerName);
            this.savingsView.setSavings(savingLoader.loadSavings());
        } catch (final IOException e) {
           throw new RuntimeException("Error while loading savings", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadSaving(final Saving saving) {
        this.controller.start(saving);
    }
}
