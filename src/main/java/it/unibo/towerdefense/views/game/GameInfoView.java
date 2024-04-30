package it.unibo.towerdefense.views.game;

import it.unibo.towerdefense.models.game.GameDTO;
import it.unibo.towerdefense.views.View;

/**
 * Interface that models the game info view.
 */
public interface GameInfoView extends View {

    /**
     * Sets the view state.
     * @param info the gameDTO
     */
    void setGameInfo(GameDTO info);
}
