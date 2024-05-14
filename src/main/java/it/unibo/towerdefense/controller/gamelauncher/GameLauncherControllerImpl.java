package it.unibo.towerdefense.controller.gamelauncher;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.view.View;

/**
 * Implementation of the GameLauncherController interface.
 */
public class GameLauncherControllerImpl implements GameLauncherController {

    private static final List<Size> RESOLUTIONS = Constants.RESOLUTIONS;

    private final View view;
    private final BiConsumer<String, Size> start;

    private String playerName;
    private int resolution;

    /**
     * Constructor for the GameLauncherControllerImpl class.
     * @param view the main view of the game
     * @param start the consumer to start the game
     */
    public GameLauncherControllerImpl(final View view, final BiConsumer<String, Size> start) {
        this.start = start;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.view.displayLauncher(this);
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
        this.start.accept(
            playerName,
            RESOLUTIONS.get(resolution)
        );
    }
}
