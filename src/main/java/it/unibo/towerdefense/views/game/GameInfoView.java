package it.unibo.towerdefense.views.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.utils.patterns.Observer;
import it.unibo.towerdefense.views.View;

/**
 * Interface that models the game info view.
 */
public interface GameInfoView extends View, Observer<GameDTO> {

}
