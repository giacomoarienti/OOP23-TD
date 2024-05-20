package it.unibo.towerdefense.view.game;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * Game info view implementation.
 */
public class GameRendererImpl implements GameRenderer {

    private final Renderer renderer;

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
        renderer.renderInfo(panel);
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
