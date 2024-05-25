package it.unibo.towerdefense.view.scoreboard;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.model.score.Score;

/**
 * Scoreboard View implementation.
 */
public class ScoreboardViewImpl implements ScoreboardView {

    private static final String CLOSE = "Close";
    private static final String NO_SCORES_AVAILABLE = "No scores available";
    private static final String COLUMN_1 = "NAME";
    private static final String COLUMN_2 = "WAVE";
    private static final int BORDER_SIZE = 10;
    private static final int RIGHT_BORDER = 0;

    private final ScoreboardDTO scoreboard;

    /**
     * Constructor for ScoreboardViewImpl.
     * @param scoreboard the scoreboard to be displayed
     */
    public ScoreboardViewImpl(final ScoreboardDTO scoreboard) {
        this.scoreboard = scoreboard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Runnable onClose) {
        // create main panel
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(
            BorderFactory.createEmptyBorder(
                BORDER_SIZE,
                BORDER_SIZE,
                BORDER_SIZE,
                RIGHT_BORDER
            )
        );
        // create inner pnl for differing layout and add to main panel
        final JPanel innerPnl = new JPanel();
        innerPnl.setLayout(new BoxLayout(innerPnl, BoxLayout.Y_AXIS));
        // if no scores are available, display a message
        if (this.scoreboard.getScores().isEmpty()) {
            final JLabel noSavingsLabel = new JLabel(NO_SCORES_AVAILABLE);
            noSavingsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            noSavingsLabel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, BORDER_SIZE, 0)
            );
            innerPnl.add(noSavingsLabel);
        } else {
            // add scoreboard header
            innerPnl.add(new ScoreboardEntry(COLUMN_1, COLUMN_2, true));
            // for each scoreboard entry, create a panel and add to inner panel
            for (final Score entry: this.scoreboard.getTopTenScores()) {
                innerPnl.add(
                    new ScoreboardEntry(
                        entry.getName(),
                        String.valueOf(entry.getWave()),
                        false
                    )
                );
            }
        }
        // create close button
        final JButton closeButton = new JButton(CLOSE);
        closeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> onClose.run());
        // create scroll panel
        final JScrollPane scrollPane = new JScrollPane(innerPnl);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // add components to the main panel
        panel.add(scrollPane);
        panel.add(closeButton);
        // return the main panel
        return panel;
    }

    private static class ScoreboardEntry extends JPanel {
        private static final long serialVersionUID = 1L;
        private static final String FONT_NAME = "Calibri";
        private static final int ENTRY_V_SPACING = 20;
        private static final int FONT_SIZE = 24;

        private static JLabel buildText(final String text, final boolean bold) {
            final JLabel label = new JLabel(text);
            label.setFont(new Font(FONT_NAME, bold ? Font.BOLD : Font.PLAIN, FONT_SIZE));
            return label;
        }

        ScoreboardEntry(final String col1, final String col2, final boolean header) {
            // set layout
            final GridLayout layout = new GridLayout(1, 2);
            layout.setHgap(ENTRY_V_SPACING);
            this.setLayout(layout);
            // set horizontal alignment
            this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            // add text
            this.add(buildText(col1, header));
            this.add(buildText(String.valueOf(col2), header));
        }
    }
}
