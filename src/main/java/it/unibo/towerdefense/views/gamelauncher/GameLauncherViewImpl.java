package it.unibo.towerdefense.views.gamelauncher;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.controllers.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.models.engine.Size;

/**
 * Implementation of the GameLauncherView interface.
 */
public class GameLauncherViewImpl implements GameLauncherView {

    private static final String START_BUTTON = "Start";
    private static final String PLAYER_NAME_LABEL = "Player Name: ";
    private static final String RESOLUTION_LABEL = "Select resolution: ";
    private static final String TITLE = Constants.GAME_NAME + " - Launcher";
    private static final int WIDTH_PROPORTION = 5;
    private static final int HEIGHT_PROPORTION = 3;

    private final JFrame frame;
    private final GameLauncherController controller;

    /**
     * Creates a new window with the game settings.
     * @param controller the game launcher controller
     */
    public GameLauncherViewImpl(final GameLauncherController controller) {
        this.controller = controller;
        // create the frame
        this.frame = new JFrame(TITLE);
        this.frame.setLayout(new FlowLayout());
        // create the UI container
        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        // create the resolution panel
        final JPanel resolutionPanel = new JPanel();
        resolutionPanel.setLayout(new BoxLayout(resolutionPanel, BoxLayout.X_AXIS));
        final List<String> resolutionsStrings = this.getResolutionStrings();
        // create the combo box and label
        final JComboBox<String> resolutionsBox = new JComboBox<String>(
            resolutionsStrings.toArray(String[]::new)
        );
        final JLabel resolutionLabel = new JLabel(RESOLUTION_LABEL);
        resolutionPanel.add(resolutionLabel);
        resolutionPanel.add(resolutionsBox);
        // create name panel
        final JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        // create name label and input
        final JLabel nameLabel = new JLabel(PLAYER_NAME_LABEL);
        final JTextField nameField = new JTextField();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        // create the start button
        final JButton startButton = new JButton(START_BUTTON);
        startButton.addActionListener(e -> {
            // start the game
            this.start(nameField.getText(), resolutionsBox.getSelectedIndex());
        });
        // add the components to the container
        container.add(namePanel);
        container.add(resolutionPanel);
        container.add(startButton);
        // add the container to the frame
        this.frame.add(container);
    }

    /**
     * {@inheritDoc}
     */
    public void display() {
        // calc and set starting frame size
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth() / WIDTH_PROPORTION;
        final int height = (int) screen.getHeight() / HEIGHT_PROPORTION;
        this.frame.setSize(width, height);
        // set default closing operation
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // center the frame
        frame.setLocationRelativeTo(null);
        // set frame not resizable
        this.frame.setResizable(false);
        // push frame on screen
        this.frame.setVisible(true);
    }

    private void start(final String name, final int resolutionIndex) {
        try {
            // set the player name and resolution
            this.controller.setPlayerName(name);
            this.controller.selectResolution(resolutionIndex);
        } catch (final Exception e) {
            // show an error message
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // close the launcher
        this.frame.dispose();
        // start the game
        this.controller.startGame();
    }

    private List<String> getResolutionStrings() {
        // get the available resolutions
        final List<Size> resolutions = this.controller.getResolutions();
        return resolutions.stream().map(Object::toString).toList();
    }
}
