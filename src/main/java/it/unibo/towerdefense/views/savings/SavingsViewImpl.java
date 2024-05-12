package it.unibo.towerdefense.views.savings;

import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
        // create main panel
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // create a panel for each saving
        for (final Saving saving: this.savings) {
            mainPanel.add(
                new SavingsPanel(
                    saving,
                    onClose
                )
            );
        }
        // return the main panel
        return mainPanel;
    }

    private class SavingsPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final Saving saving;
        private final Runnable onClose;

        private void onClick() {
            controller.loadSaving(this.saving);
            this.onClose.run();
        }

        public SavingsPanel(final Saving saving, final Runnable onClose) {
            this.saving = saving;
            this.onClose = onClose;
            // build the view
            this.add(new JLabel(saving.getName()));
            // create load button and add it to the panel
            final JButton loadButton = new JButton("Load");
            loadButton.addActionListener(e -> onClick());
            this.add(loadButton);
        }
    }
}
