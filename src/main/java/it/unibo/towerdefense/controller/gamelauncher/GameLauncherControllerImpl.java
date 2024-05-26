package it.unibo.towerdefense.controller.gamelauncher;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.view.View;

/**
 * Implementation of the GameLauncherController interface.
 */
public class GameLauncherControllerImpl implements GameLauncherController {

    private final List<Size> resolutions;
    private final Consumer<GameLauncherController> run;
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
        this.run = controller -> view.displayLauncher(controller);
        // set resolutions
        final Size maxResolution = view.getMaxResolution();
        this.resolutions = Constants.RESOLUTIONS.stream()
            .filter(res -> res.getWidth() <= maxResolution.getWidth()
                && res.getHeight() <= maxResolution.getHeight()
            )
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.run.accept(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Size> getResolutions() {
        return Collections.unmodifiableList(this.resolutions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectResolution(final int selection) {
        if (selection < 0 || selection >= this.resolutions.size()) {
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
            this.resolutions.get(resolution)
        );
    }
}
