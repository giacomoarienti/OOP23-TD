package it.unibo.towerdefense.controller.menu;

import java.util.function.Consumer;

import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.model.saves.Save;
import it.unibo.towerdefense.view.View;

/**
 * Class implementing the StartMenuController methods.
 */
public class StartMenuControllerImpl implements StartMenuController {

    private final Controller controller;
    private final Consumer<StartMenuController> run;

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
        this.run = menuController -> view.displayStartMenu(menuController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.run.accept(this);
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
    public void play(final Save save) {
        this.controller.start(save);
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
    public void displaySaves() {
        this.controller.displaySaves();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayScoreboard() {
        this.controller.displayScoreboard();
    }
}
