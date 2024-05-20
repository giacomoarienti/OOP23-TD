package it.unibo.towerdefense.view.game;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.game.ControlAction;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.game.GameStatus;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * Game info view implementation.
 */
public class GameRendererImpl implements GameRenderer {

    private final Renderer renderer;
    private final List<Observer<ControlAction>> observers =
        new ArrayList<>();

    /**
     * Zero-argument constructor.
     */
    public GameRendererImpl(final Renderer renderer) {
        this.renderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GameDTO dto) {
        // build the panel
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // add title
        final var title = new Title("Statistics:");
        panel.add(title);
        // add wave, money and lives labels
        panel.add(new JLabel("Player: " + dto.getPlayerName()));
        panel.add(new JLabel("Wave: " + dto.getWave()));
        panel.add(new JLabel("Money: " + dto.getMoney()));
        panel.add(new JLabel("Lives: " + dto.getLives()));
        // render the created panel
        renderer.renderGame(panel);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GameStatus status) {
        // build the panel
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        // add stop resume and quit buttons
        final var pauseButton = new JButton("Pause");
        final var resumeButton = new JButton("Resume");
        final var quitButton = new JButton("Quit");
        // set callbacks
        pauseButton.addActionListener(e -> this.notifyObservers(ControlAction.PAUSE));
        resumeButton.addActionListener(e -> this.notifyObservers(ControlAction.RESUME));
        quitButton.addActionListener(e -> this.notifyObservers(ControlAction.QUIT));
        // disable the buttons if the game is not running
        switch (status) {
            case PLAYING -> {
                resumeButton.setEnabled(false);
            }
            case PAUSE -> {
                pauseButton.setEnabled(false);
            }
            case GAME_OVER -> {
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(false);
            }
        }
        // add the buttons to the panel
        panel.add(pauseButton);
        panel.add(resumeButton);
        panel.add(quitButton);
        // render the created panel
        renderer.renderControls(panel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addControlsObserver(final Observer<ControlAction> observer) {
        this.observers.add(observer);
    }

    private void notifyObservers(final ControlAction action) {
        this.observers.forEach(o -> o.notify(action));
    }

    private static class Title extends JLabel {
        private static final long serialVersionUID = 1L;
        private static final String FONT = "Arial";
        private static final int BOTTOM_BORDER = 5;
        private static final int SIZE = 20;

        public Title(final String text) {
            super(text);
            this.setFont(new Font(FONT, Font.BOLD, SIZE));
            this.setBorder(
                BorderFactory.createEmptyBorder(0, 0, BOTTOM_BORDER, 0)
            );
        }
    }
}
