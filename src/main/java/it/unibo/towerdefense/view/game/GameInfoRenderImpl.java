package it.unibo.towerdefense.view.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;

/**
 * Interface that models the game info view.
 */
public interface GameInfoRenderImpl {

    /**
     * Render the game info.
     * @param dto the game dto to render.
     */
    void render(GameDTO dto);
}
