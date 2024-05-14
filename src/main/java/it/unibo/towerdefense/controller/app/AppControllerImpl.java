package it.unibo.towerdefense.controller.app;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gameloop.GameLoopController;
import it.unibo.towerdefense.controller.gameloop.GameLoopControllerImpl;
import it.unibo.towerdefense.controller.mediator.ControllerMediator;
import it.unibo.towerdefense.controller.mediator.ControllerMediatorImpl;
import it.unibo.towerdefense.controller.menu.MenuController;
import it.unibo.towerdefense.controller.menu.MenuControllerImpl;
import it.unibo.towerdefense.model.savingloader.saving.Saving;
import it.unibo.towerdefense.view.graphics.GameRendererImpl;
import it.unibo.towerdefense.view.modal.ModalContent;
import it.unibo.towerdefense.view.window.Window;

/**
 * Game controller implementation.
 */
public class AppControllerImpl implements AppController {

    private final static Logger logger =
        LoggerFactory.getLogger(AppControllerImpl.class);
    private final static Size MAP_SIZE = Constants.MAP_SIZE; // might be a variable in the future

    private final MenuController menuController;
    private final Window window;
    private final String playerName;

    private ControllerMediator masterController;
    private GameLoopController loopController;

    /**
     * Constructor with Window.
     * @param playerName the player name
     * @param window the interface's window
     */
    public AppControllerImpl(final String playerName, final Window window) {
        this.window = window;
        this.playerName = playerName;
        // instantiate menu controller
        this.menuController = new MenuControllerImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        logger.info("run()");
        // display the window
        this.window.display();
        // display the StartMenu
        this.menuController.displayStartMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info("start()");
        // instantiate the game loop and mediator controllers
        this.masterController = new ControllerMediatorImpl(
            playerName,
            MAP_SIZE,
            new GameRendererImpl(MAP_SIZE, window)
        );
        this.startGameLoop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Saving saving) {
        logger.info("start() with saving");
        // instantiate the game loop and mediator controllers
        this.masterController = new ControllerMediatorImpl(
            saving,
            new GameRendererImpl(MAP_SIZE, window)
        );
        this.startGameLoop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        if (Objects.isNull(this.masterController)) {
            throw new IllegalStateException("Game not started");
        }
        this.masterController.getGameController().pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        if (Objects.isNull(this.masterController)) {
            throw new IllegalStateException("Game not started");
        }
        this.masterController.getGameController().resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndExit() {
        logger.info("saveAndExit()");
        if (Objects.isNull(this.masterController)) {
            throw new IllegalStateException("Game not started");
        }
        // save the game and exit
        this.loopController.stop();
        this.masterController.save();
        // exit
        this.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        logger.info("exit()");
        this.window.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayModal(final String title, final ModalContent content) {
        this.window.displayModal(title, content);
    }

    private void startGameLoop() {
        this.loopController = new GameLoopControllerImpl(masterController);
        this.loopController.start();
    }
}
