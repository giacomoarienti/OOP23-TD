package it.unibo.towerdefense.controller.scoreboard;

import java.io.IOException;

import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.model.scoreboard.ScoreboardImpl;
import it.unibo.towerdefense.view.View;

/**
 * Implementation of the ScoreboardController interface.
 */
public class ScoreboardControllerImpl implements ScoreboardController {

    private final View view;
    private final ScoreboardDTO dto;

    /**
     * Constructor for the ScoreboardControllerImpl class.
     * @param view the main view of the game
     */
    public ScoreboardControllerImpl(final View view) {
        this.view = view;
        // load scoreboard and save it
        try {
            final var scoreboard = new ScoreboardImpl();
            scoreboard.loadScores();
            this.dto = new ScoreboardDTO(scoreboard);
        } catch (final IOException e) {
            throw new RuntimeException("Error loading scoreboard");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.view.displayScoreboard(this.dto);
    }

}
