package it.unibo.towerdefense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        // display Window
        final Window window = new WindowImpl();
        window.display();
    }
}
