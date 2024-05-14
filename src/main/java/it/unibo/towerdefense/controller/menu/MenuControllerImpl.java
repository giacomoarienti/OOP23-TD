package it.unibo.towerdefense.controller.menu;

import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.model.saving.Saving;

/**
 * Class implementing the Menu methods.
 */
public class MenuControllerImpl implements MenuController {

    private final Controller controller;

    /**
     * Constructor with GameController.
     * @param controller the instance of the GameController
     */
    public MenuControllerImpl(final Controller controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        this.controller.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Saving saving) {
        this.controller.start(saving);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        this.controller.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings() {
        this.controller.displaySavings();
    }
}
