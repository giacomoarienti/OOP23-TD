package it.unibo.towerdefense.controllers.menu;

import it.unibo.towerdefense.controllers.game.GameController;

/**
 * Class implementing the Menu methods.
 */
public class MenuControllerImpl implements MenuController {

    private final GameController gameController;

    /**
     * Constructor with GameController.
     * @param gameController the instance of the GameController
     */
    public MenuControllerImpl(final GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameSelection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameSelection'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeGame() {
        // TODO: remove PauseMenuView
        this.gameController.resumeGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseGame() {
        // TODO: pause the game and display the PauseMenuView
        this.gameController.pauseGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

}
