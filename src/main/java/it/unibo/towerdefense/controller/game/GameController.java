package it.unibo.towerdefense.controller.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.controller.Controller;

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
