package it.unibo.towerdefense.views.game;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.models.game.GameInfo;

/**
 * Game info view implementation.
 */
public class GameInfoViewImpl implements GameInfoView {

    private GameInfo gameInfo;

    /**
     * Zero-argument constructor.
     */
    public GameInfoViewImpl() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // add wave, money and lives labels
        panel.add(new JLabel("Wave: " + this.gameInfo.wave()));
        panel.add(new JLabel("Money: " + this.gameInfo.money()));
        panel.add(new JLabel("Lives: " + this.gameInfo.lives()));
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameInfo(final GameInfo gameInfo) {
        // store a copy of the game info
        this.gameInfo = gameInfo.copy();
    }
}
