package it.unibo.towerdefense.view;

import java.util.Objects;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.MenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherViewImpl;
import it.unibo.towerdefense.view.graphics.GameRenderer;
import it.unibo.towerdefense.view.graphics.GameRendererImpl;
import it.unibo.towerdefense.view.menus.StartMenuViewImpl;
import it.unibo.towerdefense.view.savings.SavingsViewImpl;
import it.unibo.towerdefense.view.window.Window;
import it.unibo.towerdefense.view.window.WindowImpl;

public class ViewImpl implements View {

    private Window window;
    private GameRenderer gameRenderer;

    /**
     * Constructor for the ViewImpl class.
     */
    public ViewImpl() {
    }

    @Override
    public void displayLauncher(final GameLauncherController controller) {
        // instantiate the game launcher view
        final var gameLauncherView = new GameLauncherViewImpl(controller);
        gameLauncherView.display();
    }

    @Override
    public void displayGame(final Size size) {
        this.window = new WindowImpl(size);
        this.window.display();
    }

    @Override
    public void displaySavings(final SavingsController controller) {
        final var savingsView = new SavingsViewImpl(controller);
        this.window.displayModal("Savings", savingsView);
    }

    @Override
    public void displayStartMenu(final MenuController controller) {
        final var startMenu = new StartMenuViewImpl(controller);
        this.window.displayModal("Start Menu", startMenu);
    }

    @Override
    public void close() {
        this.window.close();
    }

    @Override
    public void createGameRenderer(final Size size) {
        this.gameRenderer = new GameRendererImpl(size, this.window);
    }

    @Override
    public GameRenderer getGameRenderer() {
        if (Objects.isNull(gameRenderer)) {
            throw new IllegalStateException("GameRenderer not created yet");
        }
        return this.gameRenderer;
    }
}
