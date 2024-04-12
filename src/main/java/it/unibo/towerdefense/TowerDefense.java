package it.unibo.towerdefense;

import java.io.IOException;

import it.unibo.towerdefense.utils.file.FileUtils;

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
        // initialize game loop and start it
        final GameLoop.Builder gameLoopBuilder = new GameLoop.Builder();
        final GameLoop gameLoop = gameLoopBuilder.build();
        gameLoop.start();
    }
}
