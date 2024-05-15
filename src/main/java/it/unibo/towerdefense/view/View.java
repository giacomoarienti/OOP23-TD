package it.unibo.towerdefense.view;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;
import it.unibo.towerdefense.view.graphics.GameRenderer;

public interface View {

    /**
     * Display the GameLauncherView.
     * @param controller the Controller of the GameLauncherView
     */
    void displayLauncher(GameLauncherController controller);

    /**
     * Display the main game window.
     * @param size the size of the window
     */
    void displayWindow(Size size);

    /**
     * Display the StartMenu.
     * @param menuController the Controller of the StartMenuView
     */
    void displayStartMenu(StartMenuController menuController);

    /**
     * Display the saving selection view.
     * @param savingsController the Controller of the SavingsView
     */
    void displaySavings(SavingsController savingsController);

    /**
     * Close the window if present.
     */
    void close();

    /**
     * Creates an instance of the GameRenderer.
     * @param size the size of the renderer
     */
    void createGameRenderer(Size size);

    /**
     * Get the GameRenderer.
     * @return the GameRenderer
     */
    GameRenderer getGameRenderer();

    /**
     * Display the scoreboard.
     * @param dto the ScoreboardDTO
     */
    void displayScoreboard(ScoreboardDTO dto);
}