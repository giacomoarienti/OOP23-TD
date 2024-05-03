package it.unibo.towerdefense.controllers.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.game.GameLoopController;
import it.unibo.towerdefense.controllers.game.GameLoopControllerImpl;
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
    private final Window window;

    /**
     * Constructor with Window.
     * @param playerName the player name
     * @param window the interface's window
     */
    public AppControllerImpl(final String playerName, final Window window) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.window = window;
        // instantiate controller
        this.menuController = new MenuControllerImpl(this);
        this.loopController = new GameLoopControllerImpl(
            playerName,
            new GameRendererImpl(window)
        );
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
        this.loopController.pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.loopController.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndExit() {
        logger.info("saveAndExit()");
        this.loopController.save();
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
