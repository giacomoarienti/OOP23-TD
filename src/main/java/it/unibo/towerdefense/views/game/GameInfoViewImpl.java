package it.unibo.towerdefense.views.game;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.FlowLayout;

/**
 * Game info view implementation.
 */
public class GameInfoViewImpl implements GameInfoView {

    private int wave;
    private int money;
    private int lives;

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
        panel.add(new JLabel("Wave: " + this.wave));
        panel.add(new JLabel("Money: " + this.money));
        panel.add(new JLabel("Lives: " + this.lives));
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWave(final int wave) {
        this.wave = wave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMoney(final int money) {
        this.money = money;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLives(final int lives) {
        this.lives = lives;
    }
}
