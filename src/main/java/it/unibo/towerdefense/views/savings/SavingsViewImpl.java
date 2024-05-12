package it.unibo.towerdefense.views.savings;

import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.controllers.savings.SavingsController;
import it.unibo.towerdefense.models.savingloader.saving.Saving;

/**
 * Savings View implementation.
 *
 */
public class SavingsViewImpl implements SavingsView {

    private final SavingsController controller;
    private List<Saving> savings;

    /**
     * Create a new instance of SavingsViewImpl.
     * @param controller the view's controller
     */
    public SavingsViewImpl(final SavingsController controller) {
        this.controller = controller;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setSavings(List<Saving> savings) {
        this.savings = Collections.unmodifiableList(savings);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Runnable onClose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }
}
