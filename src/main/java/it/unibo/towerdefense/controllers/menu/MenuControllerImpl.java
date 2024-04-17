package it.unibo.towerdefense.controllers.menu;

import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.views.menus.StartMenuViewImpl;
import it.unibo.towerdefense.views.modal.ModalContent;
import it.unibo.towerdefense.views.window.Window;

/**
 * Class implementing the Menu methods.
 */
public class MenuControllerImpl implements MenuController {

    private final GameController gameController;
    private final Window window;

    /**
     * Constructor with GameController.
     * @param gameController the instance of the GameController
     * @param window the instance of the Window
     */
    public MenuControllerImpl(final GameController gameController, final Window window) {
        this.gameController = gameController;
        this.window = window;
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
    public void exit() {
        this.gameController.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayStartMenu() {
        final ModalContent startMenu = new StartMenuViewImpl(this);
        this.window.displayModal("Start Menu", startMenu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavingSelection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameSelection'");
    }
}
