package it.unibo.towerdefense.view.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.view.GameView;

/**
 * Interface that models the game info view.
 */
public interface GameInfoView extends GameView, Observer<GameDTO> {

}
