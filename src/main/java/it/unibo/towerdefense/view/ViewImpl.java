package it.unibo.towerdefense.view;

import java.util.Objects;

import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherViewImpl;
import it.unibo.towerdefense.view.graphics.GameRenderer;
import it.unibo.towerdefense.view.graphics.GameRendererImpl;
import it.unibo.towerdefense.view.menus.StartMenuViewImpl;
import it.unibo.towerdefense.view.savings.SavingsViewImpl;
import it.unibo.towerdefense.view.scoreboard.ScoreboardViewImpl;
import it.unibo.towerdefense.view.window.Window;
import it.unibo.towerdefense.view.window.WindowImpl;

/**
 * Implementation of the View interface.
 */
public class ViewImpl implements View {

    private Window window;
    private GameRenderer gameRenderer;

    /**
     * Empty constructor.
     */
    public ViewImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayLauncher(final GameLauncherController controller) {
        // instantiate the game launcher view
        final var gameLauncherView = new GameLauncherViewImpl(controller);
        gameLauncherView.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayWindow(final Size size) {
        this.window = new WindowImpl(size);
        this.window.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings(final SavingsController controller) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        final var savingsView = new SavingsViewImpl(controller);
        this.window.displayModal("Savings", savingsView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayStartMenu(final StartMenuController controller) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        final var startMenu = new StartMenuViewImpl(controller);
        this.window.displayModal("Start Menu", startMenu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        this.window.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGameRenderer(final Size size) {
        this.gameRenderer = new GameRendererImpl(size, this.window);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameRenderer getGameRenderer() {
        if (Objects.isNull(gameRenderer)) {
            throw new IllegalStateException("GameRenderer not created yet");
        }
        return this.gameRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayScoreboard(final ScoreboardDTO dto) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        this.window.displayModal("Scoreboard", new ScoreboardViewImpl(dto));
    }
}
