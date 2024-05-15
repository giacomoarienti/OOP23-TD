package it.unibo.towerdefense.view;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;
import it.unibo.towerdefense.model.game.GameStatusEnum;

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
     * Display the scoreboard.
     * @param dto the ScoreboardDTO
     */
    void displayScoreboard(ScoreboardDTO dto);

    /**
     * Sets the map size.
     * @param mapSize
     */
    void setMapSize(Size mapSize);

    /**
     * Display the game info.
     * @param dto the gamedto to render.
     */
    void renderGameInfo(GameDTO dto);

    /**
    * Render the enemies currently alive.
     *
     * @param enemies the enemies to render
     */
    void renderEnemies(Stream<EnemyInfo> enemies);

    /**
     * Render all map cells.
     * @param path pathCells positions
     * @param buildables BuildableCells positions
     */
    void renderMap(Stream<LogicalPosition> path, Stream<LogicalPosition> buildables);

    /**
     * Show the menù of buildin options for the selected cell.
     * @param options defense description and if it is purchasable
     */
    void showBuildingOptions(Stream<Pair<DefenseDescription, Boolean>> options);

    /**
     * Hilight the current selected cell.
     * @param selected selected cell position
     */
    void showSelected(LogicalPosition selected);

    /**
     * Renders the current game state.
     * @param gameState the game state to render
     */
    void renderState(GameStatusEnum gameState);
}
