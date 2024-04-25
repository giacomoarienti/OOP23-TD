package it.unibo.towerdefense.controllers.gamelauncher;

import java.util.Collections;
import java.util.List;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.controllers.app.AppController;
import it.unibo.towerdefense.controllers.app.AppControllerImpl;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.models.engine.SizeImpl;
import it.unibo.towerdefense.views.window.Window;
import it.unibo.towerdefense.views.window.WindowImpl;
import it.unibo.towerdefense.views.gamelauncher.GameLauncherView;
import it.unibo.towerdefense.views.gamelauncher.GameLauncherViewImpl;

/**
 * Implementation of the GameLauncher interface.
 */
public class GameLauncherControllerImpl implements GameLauncherController {
;
    private static final List<Size> resolutions = List.of(
        new SizeImpl(1920, 1080),
        new SizeImpl(1280, 720),
        new SizeImpl(1024, 768),
        new SizeImpl(800, 600)
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
        return Collections.unmodifiableList(resolutions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectResolution(final int selection) {
        if (selection < 0 || selection >= resolutions.size()) {
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
        System.out.println(name.length());
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
        final Window window = new WindowImpl(resolutions.get(this.resolution));
        // instantiate the app controller and run it
        final AppController appController = new AppControllerImpl(window);
        appController.run();
    }
}
