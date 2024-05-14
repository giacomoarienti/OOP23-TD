package it.unibo.towerdefense.controller.gamelauncher;

import java.util.Collections;
import java.util.List;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;
import it.unibo.towerdefense.controller.app.AppController;
import it.unibo.towerdefense.controller.app.AppControllerImpl;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherView;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherViewImpl;
import it.unibo.towerdefense.view.window.Window;
import it.unibo.towerdefense.view.window.WindowImpl;

/**
 * Implementation of the GameLauncher interface.
 */
public class GameLauncherControllerImpl implements GameLauncherController {

    private static final List<Size> RESOLUTIONS = List.of(
        new SizeImpl(3440, 1440),
        new SizeImpl(2560, 1440),
        new SizeImpl(1920, 1080),
        new SizeImpl(1280, 720)
    );

    private String playerName;
    private int resolution;

    /**
     * Zero-argument constructor.
     */
    public GameLauncherControllerImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        // instantiate the game launcher view
        final GameLauncherView gameLauncherView = new GameLauncherViewImpl(this);
        gameLauncherView.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Size> getResolutions() {
        return Collections.unmodifiableList(RESOLUTIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectResolution(final int selection) {
        if (selection < 0 || selection >= RESOLUTIONS.size()) {
            throw new IllegalArgumentException("Invalid resolution selection");
        }
        this.resolution = selection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerName(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid player name");
        }
        if (name.length() > Constants.MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Player name too long, max " + Constants.MAX_NAME_LENGTH + " characters allowed");
        }
        this.playerName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        // instantiate the game window
        final Window window = new WindowImpl(RESOLUTIONS.get(this.resolution));
        // instantiate the app controller and run it
        final AppController appController = new AppControllerImpl(playerName, window);
        appController.run();
    }
}
