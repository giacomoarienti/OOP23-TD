package it.unibo.towerdefense.controller.savings;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controller.app.AppController;
import it.unibo.towerdefense.model.savingloader.SavingLoaderImpl;
import it.unibo.towerdefense.model.savingloader.saving.Saving;
import it.unibo.towerdefense.view.savings.SavingsView;
import it.unibo.towerdefense.view.savings.SavingsViewImpl;

/**
 * Implementation of SavingsController.
 */
public class SavingsControllerImpl implements SavingsController {

    private final static Logger logger =
        LoggerFactory.getLogger(SavingsControllerImpl.class);

    private final AppController appController;
    private final SavingsView savingsView;

    /**
     * Loads savings from the SavingLoader.
     * @param playerName the name of the player
     * @param appController the application controller
     */
    public SavingsControllerImpl(final AppController appController) {
        this.appController = appController;
        this.savingsView = new SavingsViewImpl(this);
        // load savings
        try {
            final var savingLoader = new SavingLoaderImpl(
                appController.getPlayerName()
            );
            this.savingsView.setSavings(
                savingLoader.loadSavings()
            );
        } catch (final IOException e) {
           throw new RuntimeException("Error while loading savings", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadSaving(final Saving saving) {
        this.appController.start(saving);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings() {
        logger.debug("displaySavings()");
        this.appController.displayModal("Savings", this.savingsView);
    }
}
