package it.unibo.towerdefense.view.game;

import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Interface that models the game info view.
 */
public interface GameInfoView extends Observer<GameDTO> {

    /**
     * Returns whether the view should be re-rendered.
     * @return true if the view should be rendered, false otherwise.
     */
    boolean shouldRender();

    /**
     * Builds the View and returns its JPanel.
     * @return the JPanel with the view's content.
     */
    JPanel build();
}
