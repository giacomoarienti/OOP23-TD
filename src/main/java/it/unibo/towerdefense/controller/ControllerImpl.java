package it.unibo.towerdefense.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherControllerImpl;
import it.unibo.towerdefense.controller.gameloop.GameLoopController;
import it.unibo.towerdefense.controller.gameloop.GameLoopControllerImpl;
import it.unibo.towerdefense.controller.menu.MenuControllerImpl;
import it.unibo.towerdefense.controller.savings.SavingsControllerImpl;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.ModelManagerImpl;
import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.view.View;

public class ControllerImpl implements Controller {

    private final static Logger logger =
        LoggerFactory.getLogger(ControllerImpl.class);
    private final static Size MAP_SIZE = Constants.MAP_SIZE; // might be a variable in the future

    private final View view;

    private String playerName;
    private ModelManager manager;
    private GameLoopController loopController;

    /**
     * Constructor for the ControllerImpl class.
     * @param view the main view of the game
     */
    public ControllerImpl(final View view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void launch() {
        final var gameLauncherController = new GameLauncherControllerImpl();
        this.view.displayLauncher(gameLauncherController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(final String playerName, final Size resolution) {
        logger.info("run()");
        this.playerName = playerName;
        // display the game window
        this.view.displayGame(resolution);
        // display the StartMenu
        final var menu = new MenuControllerImpl(this);
        this.view.displayStartMenu(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info("start()");
        // instantiate the game loop and mediator controllers
        this.manager = new ModelManagerImpl(MAP_SIZE, playerName);
        this.startGameLoop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Saving saving) {
        logger.info("start() with saving");
        // instantiate the game loop and mediator controllers
        this.manager =  new ModelManagerImpl(saving);
        this.startGameLoop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndExit() {
        logger.info("saveAndExit()");
        if (Objects.isNull(this.manager)) {
            throw new IllegalStateException("Game not started");
        }
        // save the game and exit
        this.loopController.stop();
        this.save();
        // exit
        this.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        logger.info("exit()");
        this.view.close();
    }

    @Override
    public void resume() {
        this.manager.getGame().resume();
    }

    @Override
    public void stop() {
        this.loopController.stop();
    }

    @Override
    public boolean isPlaying() {
        return this.manager.getGame().isPlaying();
    }

    public void update() {
        // TODO implement here
    }

    public void render() {
        // TODO implement here
    }

    public void displaySavings() {
        final var savingsController = new SavingsControllerImpl(
            this.playerName,
            this
        );
        this.view.displaySavings(savingsController);
    }

    private void startGameLoop() {
        this.loopController = new GameLoopControllerImpl(this);
        this.loopController.start();
    }
}
