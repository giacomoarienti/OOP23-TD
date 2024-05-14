package it.unibo.towerdefense.controller.gamelauncher;

import java.util.Collections;
import java.util.List;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherView;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherViewImpl;

/**
 * Implementation of the GameLauncher interface.
 */
public class GameLauncherControllerImpl implements GameLauncherController {

    private static final List<Size> RESOLUTIONS = Constants.RESOLUTIONS;

    private Controller controller;
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
        controller.run(
            playerName,
            RESOLUTIONS.get(resolution)
        );
    }
}
