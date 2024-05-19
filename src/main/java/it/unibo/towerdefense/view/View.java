package it.unibo.towerdefense.view;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.GameState;
import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;

/**
 * Interface for the main View.
 */
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
    void renderGame(GameDTO dto);

    /**
     * Renders the current state of the synchronous part of the game.
     * - the enemies currently alive
     * - all map cells
     * - the defenses currently built
     * @param s the state of the game
     */
    void render(GameState s);

    /**
     * Hilight the current selected cell.
     * @param selected selected cell position
     */
    void showSelected(LogicalPosition selected);

    /**
     * Add an observer to the map cell selection.
     * Whenever a cell is selected, the observer will be notified.
     * @param observer the observer to add
     */
    void addMapCellSelectionObserver(Observer<Position> observer);

    /**
     * Add an observer for the building menu.
     * Whenever a buying button is pushed, the observer will be notified.
     * @param observer the observer to add.
     */
    void addBuyMenuObserver(Observer<Integer> observer);
}
