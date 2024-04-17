package it.unibo.towerdefense.views.menus;

import java.util.List;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.controllers.menu.MenuController;
import it.unibo.towerdefense.views.View;

/**
 * View representing the game's Menu.
 */
public class MenuViewImpl implements View {

    private static final String MENU_LABEL = Constants.GAME_NAME;
    private static final String PLAY_LABEL = "Play";
    private static final String LOAD_GAME_LABEL = "Load game";
    private static final String QUIT_LABEL = "Quit";

    private final MenuController controller;

    /**
     * MenuView constructor passing reference to its controller.
     * @param controller the MenuController
     */
    public MenuViewImpl(final MenuController controller) {
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
        // create play, load, quit buttons
        final List<JButton> buttons = List.of(
            createButton(PLAY_LABEL, (e) -> this.controller.play()),
            createButton(LOAD_GAME_LABEL, (e) -> this.controller.savingSelection()),
            createButton(QUIT_LABEL, (e) -> this.controller.exit())
        );
        buttons.stream().forEach((button) -> innerPnl.add(button));
        return panel;
    }

    private JButton createButton(final String label, final ActionListener action) {
        final JButton button = new JButton(label);
        button.setHorizontalAlignment(JButton.CENTER);
        button.addActionListener(action);
        return button;
    }
}
