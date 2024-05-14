package it.unibo.towerdefense.controller.savings;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.model.saving.SavingsImpl;
import it.unibo.towerdefense.view.View;

/**
 * Implementation of SavingsController.
 */
public class SavingsControllerImpl implements SavingsController {

    private final Consumer<Saving> start;
    private final View view;
    private final List<Saving> savings;

    /**
     * Loads savings from the SavingsController.
     * @param playerName the player name
     * @param view the application view
     * @param start the consumer to start the game
     */
    public SavingsControllerImpl(
        final String playerName,
        final View view,
        final Consumer<Saving> start
    ) {
        this.start = start;
        this.view = view;
        // load savings
        try {
            final var savingLoader = new SavingsImpl(playerName);
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
        this.start.accept(saving);
    }
}
