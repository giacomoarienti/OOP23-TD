package it.unibo.towerdefense;

import java.io.IOException;

import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherControllerImpl;

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
     * @throws IOException if the game folder cannot be created
     */
    public static void main(final String[] args) throws IOException {
        // create game folder
        FileUtils.createGameFolder();
        // start the game launcher
        final GameLauncherController gameLauncher = new GameLauncherControllerImpl();
        gameLauncher.run();
    }
}
