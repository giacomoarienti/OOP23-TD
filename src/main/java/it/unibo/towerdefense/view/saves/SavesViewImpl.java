package it.unibo.towerdefense.view.saves;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import it.unibo.towerdefense.controller.saves.SavesController;
import it.unibo.towerdefense.model.saves.Save;

/**
 * Saves View implementation.
 *
 */
public class SavesViewImpl implements SavesView {

    private static final int BOTTOM_BORDER = 10;
    private final SavesController controller;

    /**
     * Create a new instance of SavesViewImpl.
     * @param controller the view's controller
     */
    public SavesViewImpl(final SavesController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Runnable onClose) {
        // get the list of saves
        final List<Save> saves = this.controller.getSaves();
        // create main panel
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // create inner panel
        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        // if there are no saves, display a message
        if (saves.isEmpty()) {
            final JLabel noSavesLabel = new JLabel("No saves available");
            noSavesLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            noSavesLabel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, BOTTOM_BORDER, 0)
            );
            innerPanel.add(noSavesLabel);
        }
        // create a panel for each save
        for (final Save save: saves) {
            innerPanel.add(
                new SavesPanel(
                    save,
                    onClose
                )
            );
        }
        // add close button
        final JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> onClose.run());
        // create scroll pane
        final JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // add components to the main panel
        panel.add(scrollPane);
        panel.add(closeButton);
        return panel;
    }

    private class SavesPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
        private final Save save;
        private final Runnable onClose;

        private void onClick() {
            controller.loadSave(this.save);
            this.onClose.run();
        }

        SavesPanel(final Save save, final Runnable onClose) {
            this.save = save;
            this.onClose = onClose;
            // build the view
            this.add(new JLabel(this.formatDate(save)));
            // create load button and add it to the panel
            final JButton loadButton = new JButton("Load");
            loadButton.addActionListener(e -> onClick());
            this.add(loadButton);
            // set horizontal alignment
            this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            // add bottom padding
            this.setBorder(
                BorderFactory.createEmptyBorder(0, 0, BOTTOM_BORDER, 0)
            );
        }

        private String formatDate(final Save save) {
            return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(save.getDate());
        }
    }
}
