package it.unibo.towerdefense.controllers.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.game.GameControllerImpl;
import it.unibo.towerdefense.controllers.menu.MenuController;
import it.unibo.towerdefense.controllers.menu.MenuControllerImpl;
import it.unibo.towerdefense.views.window.Window;

/**
 * Game controller implementation.
 */
public class AppControllerImpl implements AppController {

    private final Logger logger;
    private final MenuController menuController;
    private final GameController gameController;
    private final Window window;

    /**
     * Constructor with Window.
     * @param window the interface's window
     */
    public AppControllerImpl(final Window window) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.window = window;
        // instantiate controller
        this.menuController = new MenuControllerImpl(this, this.window);
        this.gameController = new GameControllerImpl();
        // TODO add controllers
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
        this.gameController.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.gameController.pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.gameController.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndExit() {
        logger.info("saveAndExit()");
        this.gameController.save();
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
}
