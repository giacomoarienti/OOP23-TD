package it.unibo.towerdefense.controllers.menu;

import it.unibo.towerdefense.controllers.app.AppController;
import it.unibo.towerdefense.controllers.savings.SavingsControllerImpl;
import it.unibo.towerdefense.views.menus.StartMenuViewImpl;
import it.unibo.towerdefense.views.modal.ModalContent;

/**
 * Class implementing the Menu methods.
 */
public class MenuControllerImpl implements MenuController {

    private final AppController appController;

    /**
     * Constructor with GameController.
     * @param appController the instance of the GameController
     * @param window the instance of the Window
     */
    public MenuControllerImpl(final AppController appController) {
        this.appController = appController;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        // TODO remove PauseMenuView
        this.appController.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        // TODO remove PauseMenuView
        this.appController.pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        // TODO remove PauseMenuView
        this.appController.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        this.appController.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayStartMenu() {
        final ModalContent startMenu = new StartMenuViewImpl(this);
        this.appController.displayModal("Start Menu", startMenu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings() {
        final var savingsController = new SavingsControllerImpl(this.appController);
        savingsController.displaySavings();
    }
}
