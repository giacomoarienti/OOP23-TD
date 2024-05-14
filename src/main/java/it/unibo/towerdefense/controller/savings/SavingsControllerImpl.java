package it.unibo.towerdefense.controller.savings;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.view.View;

/**
 * Implementation of SavingsController.
 */
public class SavingsControllerImpl implements SavingsController {

    private final Controller controller;
    private final View view;
    private final List<Saving> savings;

    /**
     * Loads savings from the SavingLoader.
     * @param controller the application controller.
     * @param view the application view.
     */
    public SavingsControllerImpl(final Controller controller, final View view) {
        this.controller = controller;
        this.view = view;
        // load savings
        try {
            final var savingLoader = new SavingLoaderImpl(controller.getPlayerName());
            this.savings = savingLoader.loadSavings();
        } catch (final IOException e) {
           throw new RuntimeException("Error while loading savings", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Saving> getSavings() {
        return Collections.unmodifiableList(this.savings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.view.displaySavings(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadSaving(final Saving saving) {
        this.controller.start(saving);
    }
}
