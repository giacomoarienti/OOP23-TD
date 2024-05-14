package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.utils.patterns.Observer;

/**
 * Interface that defines GameController methods.
 */
public interface GameController extends Controller {

    /**
     * Add observer to the Game Model.
     * @param observer the observer to be added
     */
    void addObserver(Observer<GameDTO> observer);
}
