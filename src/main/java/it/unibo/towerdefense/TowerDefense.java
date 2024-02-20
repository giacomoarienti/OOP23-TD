package it.unibo.towerdefense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.game.GameControllerImpl;
import it.unibo.towerdefense.views.View;
import it.unibo.towerdefense.views.menu.MenuViewImpl;
import it.unibo.towerdefense.views.window.Window;
import it.unibo.towerdefense.views.window.WindowImpl;

/**
 * The main class for the Tower Defense game.
 * This class contains the entry point of the game and initializes the game components.
 */
public final class TowerDefense {

    /**
     * Zero-argument constructor.
     */
    private TowerDefense() {
    }

    /**
     * The main entry point of the Tower Defense application.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        final Logger logger = LoggerFactory.getLogger(TowerDefense.class);
        logger.info("app started");
        // create window
        final Window window = new WindowImpl();
        // create gameController
        final GameController gameController = new GameControllerImpl();
        // create menu view and bind game controller
        final View menuView = new MenuViewImpl(gameController);
        menuView.display(window.getPanel());
        // display window
        window.display();
    }
}
