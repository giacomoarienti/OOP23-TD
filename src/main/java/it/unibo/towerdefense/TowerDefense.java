package it.unibo.towerdefense;

import it.unibo.towerdefense.controllers.game.GameControllerImpl;
import it.unibo.towerdefense.models.game.GameLoop;

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
        new Thread(new GameLoop(new GameControllerImpl())).start();
    }
}
