package it.unibo.towerdefense.views.menu;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.views.View;

/**
 * View representing the game's Menu.
 */
public class MenuViewImpl implements View {

    private static final String MENU_LABEL = "MENU";
    private static final String RESUME_LABEL = "Resume";
    private static final String QUIT_LABEL = "Quit";

    private final JPanel pane;

    /**
     * MenuView constructor passing reference to @param controller
     * @param controller the GameController
     */
    public MenuViewImpl(final GameController controller) {
        // create main panel
        this.pane = new JPanel(new FlowLayout());
        // create inner pnl for differing layout and add to main panel
        final JPanel innerPnl = new JPanel();
        innerPnl.setLayout(new BoxLayout(innerPnl, BoxLayout.Y_AXIS));
        innerPnl.setBackground(Color.DARK_GRAY);
        this.pane.add(innerPnl);
        // create menu title label and add to inner panel
        final JLabel titleLbl = new JLabel(MENU_LABEL);
        titleLbl.setHorizontalAlignment(JLabel.CENTER);
        innerPnl.add(titleLbl);
        // create resume label and add action listener
        final JButton resumeBtn = new JButton(RESUME_LABEL);
        resumeBtn.setHorizontalAlignment(JButton.CENTER);
        resumeBtn.addActionListener((e) -> controller.pauseGame());
        // create quit button and add action listener
        final JButton quitBtn = new JButton(QUIT_LABEL);
        resumeBtn.setHorizontalAlignment(JButton.CENTER);
        quitBtn.addActionListener((e) -> controller.exit());
        // add buttons to inner panel
        innerPnl.add(resumeBtn);
        innerPnl.add(quitBtn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getContentPane() {
        return this.pane;
    }
}
