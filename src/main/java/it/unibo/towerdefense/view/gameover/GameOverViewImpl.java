package it.unibo.towerdefense.view.gameover;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import it.unibo.towerdefense.commons.dtos.score.ScoreDTO;

/**
 * Scoreboard View implementation.
 */
public class GameOverViewImpl implements GameOverView {

    private static final int FONT_SIZE = 20;
    private static final String CLOSE = "Close";
    private static final String SCORE = "Score: ";

    private final ScoreDTO score;

    /**
     * Constructor for GameOverViewImpl.
     * @param dto the ScoreDTO object to display.
     */
    public GameOverViewImpl(final ScoreDTO dto) {
        this.score = dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Runnable onClose) {
        // create main panel
        final JPanel panel = new JPanel(new FlowLayout());
        // create inner pnl for differing layout and add to main panel
        final JPanel innerPnl = new JPanel();
        innerPnl.setLayout(new BoxLayout(innerPnl, BoxLayout.Y_AXIS));
        // score label
        final JLabel scoreLabel = new JLabel(SCORE + score.getWave());
        scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.PLAIN, FONT_SIZE));
        innerPnl.add(scoreLabel);
        // add close button
        final JButton closeButton = new JButton(CLOSE);
        closeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> onClose.run());
        innerPnl.add(closeButton);
        // return the main panel
        panel.add(innerPnl);
        return panel;
    }
}
