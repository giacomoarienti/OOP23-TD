package it.unibo.towerdefense.controllers.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.controllers.gameloop.GameLoopController;
import it.unibo.towerdefense.controllers.gameloop.GameLoopControllerImpl;
import it.unibo.towerdefense.controllers.mediator.ControllerMediator;
import it.unibo.towerdefense.controllers.mediator.ControllerMediatorImpl;
import it.unibo.towerdefense.controllers.menu.MenuController;
import it.unibo.towerdefense.controllers.menu.MenuControllerImpl;
import it.unibo.towerdefense.views.graphics.GameRendererImpl;
import it.unibo.towerdefense.views.modal.ModalContent;
import it.unibo.towerdefense.views.window.Window;

/**
 * Game controller implementation.
 */
public class AppControllerImpl implements AppController {

    private final Logger logger;
    private final MenuController menuController;
    private final GameLoopController loopController;
    private final ControllerMediator masterController;
    private final Window window;

    /**
     * Constructor with Window.
     * @param playerName the player name
     * @param window the interface's window
     */
    public AppControllerImpl(final String playerName, final Window window) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.window = window;
        final var mapSize = Constants.MAP_SIZE; // might be a variable in the future
        // instantiate controllers
        this.menuController = new MenuControllerImpl(this);
        this.masterController = new ControllerMediatorImpl(
            playerName,
            mapSize,
            new GameRendererImpl(mapSize, window)
        );
        this.loopController = new GameLoopControllerImpl(masterController);
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
        this.loopController.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.masterController.getGameController().pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.masterController.getGameController().resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndExit() {
        logger.info("saveAndExit()");
        this.masterController.save();
        this.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        logger.info("exit()");
        this.loopController.stop();
        this.window.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayModal(final String title, final ModalContent content) {
        this.window.displayModal(title, content);
    }
}
