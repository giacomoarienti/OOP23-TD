package it.unibo.towerdefense.view.game;

import it.unibo.towerdefense.commons.dtos.game.ControlAction;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.game.GameStatus;

/**
 * Interface that models the game info view.
 */
public interface GameRenderer {

    /**
     * Render the game info.
     * @param dto the game dto to render.
     */
    void render(GameDTO dto);

    /**
     * Render the controls.
     * @param status the current game status.
     */
    void render(GameStatus status);

    /**
     * Add an observer to the controls.
     * @param observer the observer to add.
     */
    void addControlsObserver(Observer<ControlAction> observer);
}
