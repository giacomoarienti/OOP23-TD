package it.unibo.towerdefense.view.menus;

import java.util.List;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.view.modal.ModalContent;

/**
 * View representing the game's Menu.
 */
public class StartMenuViewImpl implements ModalContent {

    private static final String PLAY_LABEL = "Play";
    private static final String LOAD_GAME_LABEL = "Load game";
    private static final String QUIT_LABEL = "Quit";

    private final StartMenuController controller;

    /**
     * MenuView constructor passing reference to its controller.
     * @param controller the MenuController
     */
    public StartMenuViewImpl(final StartMenuController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Runnable onClose) {
        // create main panel
        final JPanel panel = new JPanel(new FlowLayout());
        // create inner panel
        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        // create play, load, quit buttons
        final List<Component> buttons = List.of(
            createButton(PLAY_LABEL, (e) -> this.play(onClose)),
            createButton(LOAD_GAME_LABEL, (e) -> this.controller.displaySavings()),
            createButton(QUIT_LABEL, (e) -> this.close(onClose))
        );
        buttons.stream().forEach((button) -> innerPanel.add(button));
        // add inner panel to main panel
        panel.add(innerPanel);
        return panel;
    }

    private Component createButton(final String label, final ActionListener action) {
        // create button container
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        // create button
        final JButton button = new JButton(label);
        button.setHorizontalAlignment(JButton.CENTER);
        button.addActionListener(action);
        // add button to container and return it
        panel.add(button);
        return panel;
    }

    private void play(final Runnable onClose) {
        onClose.run();
        this.controller.play();
    }

    private void close(final Runnable onClose) {
        onClose.run();
        this.controller.exit();
    }
}
