package it.unibo.towerdefense;

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
        final GameLoop.Builder gameLoopBuilder = new GameLoop.Builder();
        final GameLoop gameLoop = gameLoopBuilder.build();
        gameLoop.start();
    }
}
