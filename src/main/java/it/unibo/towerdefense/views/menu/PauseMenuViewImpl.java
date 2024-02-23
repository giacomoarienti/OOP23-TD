package it.unibo.towerdefense.views.menu;

import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.views.View;

/**
 * View representing the game's Menu.
 */
public class PauseMenuViewImpl implements View {

    private static final String MENU_LABEL = "Pause";
    private static final String RESUME_LABEL = "Resume";
    private static final String QUIT_LABEL = "Quit";

    private final GameController controller;

    /**
     * MenuView constructor passing reference to controller.
     * @param controller the GameController
     */
    public PauseMenuViewImpl(final GameController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        // create main panel
        final JPanel panel = new JPanel(new FlowLayout());
        // create inner pnl for differing layout and add to main panel
        final JPanel innerPnl = new JPanel();
        innerPnl.setLayout(new BoxLayout(innerPnl, BoxLayout.Y_AXIS));
        innerPnl.setBackground(Color.DARK_GRAY);
        panel.add(innerPnl);
        // create menu title label and add to inner panel
        final JLabel titleLbl = new JLabel(MENU_LABEL);
        titleLbl.setHorizontalAlignment(JLabel.CENTER);
        innerPnl.add(titleLbl);
        // create resume label and add action listener
        final JButton resumeBtn = new JButton(RESUME_LABEL);
        resumeBtn.setHorizontalAlignment(JButton.CENTER);
        resumeBtn.addActionListener((e) -> this.controller.pauseGame());
        // create quit button and add action listener
        final JButton quitBtn = new JButton(QUIT_LABEL);
        resumeBtn.setHorizontalAlignment(JButton.CENTER);
        quitBtn.addActionListener((e) -> this.controller.exit());
        // add buttons to inner panel
        innerPnl.add(resumeBtn);
        innerPnl.add(quitBtn);
        return panel;
    }
}
