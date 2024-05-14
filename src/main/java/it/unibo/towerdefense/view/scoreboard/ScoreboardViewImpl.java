package it.unibo.towerdefense.view.scoreboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.model.scoreboard.Scoreboard;
import it.unibo.towerdefense.model.scoreboard.score.Score;
import it.unibo.towerdefense.model.scoreboard.score.ScoreboardDTO;

/**
 * Scoreboard View implementation.
 */
public class ScoreboardViewImpl implements ScoreboardView {

    private static final String COLUMN_1 = "NAME";
    private static final String COLUMN_2 = "WAVE";
    private static final int BORDER_SIZE = 10;
    private static final int RIGHT_BORDER = 0;

    private final ScoreboardDTO scoreboard;

    /**
     * Constructor for ScoreboardViewImpl.
     * @param scoreboard the scoreboard to be displayed
     */
    public ScoreboardViewImpl(final Scoreboard scoreboard) {
        this.scoreboard = new ScoreboardDTO(scoreboard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Runnable onClose) {
        // create main panel
        final JPanel panel = new JPanel(new FlowLayout());
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
        final BoxLayout layout = new BoxLayout(innerPnl, BoxLayout.Y_AXIS);
        innerPnl.setLayout(layout);
        innerPnl.setBackground(Color.DARK_GRAY);
        panel.add(innerPnl);
        // add scoreboard header
        innerPnl.add(new ScoreboardEntry(COLUMN_1, COLUMN_2, true));
        // for each scoreboard entry, create a panel and add to inner panel
        for (final Score entry : this.scoreboard.getScores()) {
            innerPnl.add(
                new ScoreboardEntry(
                    entry.getName(),
                    String.valueOf(entry.getWave()),
                    false
                )
            );
        }
        return panel;
    }


    private class ScoreboardEntry extends JPanel {
        private static final long serialVersionUID = 1L;
        private static final String FONT_NAME = "Calibri";
        private static final int ENTRY_V_SPACING = 20;
        private static final int FONT_SIZE = 24;

        private static JLabel buildText(final String text, final boolean bold) {
            final JLabel label = new JLabel(text);
            label.setFont(new Font(FONT_NAME, bold ? Font.BOLD : Font.PLAIN, FONT_SIZE));
            return label;
        }

        public ScoreboardEntry(final String col1, final String col2, final boolean header) {
            // set layout
            final GridLayout layout = new GridLayout(1, 2);
            layout.setHgap(ENTRY_V_SPACING);
            this.setLayout(layout);
            // add text
            this.add(buildText(col1, header));
            this.add(buildText(String.valueOf(col2), header));
        }
    }
}
