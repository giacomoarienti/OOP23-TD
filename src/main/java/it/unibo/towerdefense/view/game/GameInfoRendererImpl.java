package it.unibo.towerdefense.view.game;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.view.graphics.Renderer;

/**
 * Game info view implementation.
 */
public class GameInfoRendererImpl implements GameInfoRenderImpl {

    private final Renderer renderer;

    /**
     * Zero-argument constructor.
     */
    public GameInfoRendererImpl(final Renderer renderer) {
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
        // add wave, money and lives labels
        panel.add(new JLabel("Player: " + dto.getPlayerName()));
        panel.add(new JLabel("Wave: " + dto.getWave()));
        panel.add(new JLabel("Money: " + dto.getMoney()));
        panel.add(new JLabel("Lives: " + dto.getLives()));
        // render the created panel
        renderer.renderInfo(panel);
    }
}
