package it.unibo.towerdefense.views.game;

import it.unibo.towerdefense.models.game.GameInfo;
import it.unibo.towerdefense.views.View;

/**
 * Interface that models the game info view.
 */
public interface GameInfoView extends View {

    /**
     * Sets the view state.
     */
    void setGameInfo(GameInfo gameState);
}
