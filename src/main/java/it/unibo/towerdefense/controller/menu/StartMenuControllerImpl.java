package it.unibo.towerdefense.controller.menu;

import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.view.View;

/**
 * Class implementing the StartMenuController methods.
 */
public class StartMenuControllerImpl implements StartMenuController {

    private final Controller controller;
    private final View view;

    /**
     * Constructor with GameController.
     * @param controller the instance of the GameController
     * @param view the instance of the View
     */
    public StartMenuControllerImpl(
        final Controller controller,
        final View view
    ) {
        this.controller = controller;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.view.displayStartMenu(this);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayScoreboard() {
        this.controller.displayScoreboard();
    }
}
