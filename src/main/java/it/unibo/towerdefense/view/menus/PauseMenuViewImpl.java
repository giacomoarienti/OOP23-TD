package it.unibo.towerdefense.view.menus;

import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import it.unibo.towerdefense.controller.menu.MenuController;
import it.unibo.towerdefense.view.modal.ModalContent;

/**
 * View representing the game's Menu.
 */
public class PauseMenuViewImpl implements ModalContent {

    private static final String MENU_LABEL = "Pause";
    private static final String RESUME_LABEL = "Resume";
    private static final String QUIT_LABEL = "Quit";

    private final MenuController controller;

    /**
     * MenuView constructor passing reference to its controller.
     * @param controller the MenuController
     */
    public PauseMenuViewImpl(final MenuController controller) {
        this.controller = controller;
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
        innerPnl.setBackground(Color.DARK_GRAY);
        panel.add(innerPnl);
        // create menu title label and add to inner panel
        final JLabel titleLbl = new JLabel(MENU_LABEL);
        titleLbl.setHorizontalAlignment(JLabel.CENTER);
        innerPnl.add(titleLbl);
        // create resume label and add action listener
        final JButton resumeBtn = new JButton(RESUME_LABEL);
        resumeBtn.setHorizontalAlignment(JButton.CENTER);
        resumeBtn.addActionListener((e) -> this.controller.pause());
        // create quit button and add action listener
        final JButton quitBtn = new JButton(QUIT_LABEL);
        resumeBtn.setHorizontalAlignment(JButton.CENTER);
        quitBtn.addActionListener((e) -> this.close(onClose));
        // add buttons to inner panel
        innerPnl.add(resumeBtn);
        innerPnl.add(quitBtn);
        return panel;
    }

    private void close(final Runnable onClose) {
        onClose.run();
        this.controller.exit();
    }
}
