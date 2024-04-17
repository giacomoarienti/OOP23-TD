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
    public void play() {
        // TODO remove PauseMenuView
        this.gameController.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        // TODO remove PauseMenuView
        this.gameController.pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        // TODO remove PauseMenuView
        this.gameController.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void savingSelection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameSelection'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        this.gameController.exit();
    }

}
