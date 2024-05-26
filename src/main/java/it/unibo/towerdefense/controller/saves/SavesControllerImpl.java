package it.unibo.towerdefense.controller.saves;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import it.unibo.towerdefense.model.saves.Save;
import it.unibo.towerdefense.model.saves.SavesImpl;
import it.unibo.towerdefense.view.View;

/**
 * Implementation of SavesController.
 */
public class SavesControllerImpl implements SavesController {

    private final Consumer<Save> start;
    private final Consumer<SavesController> run;
    private final List<Save> saves;

    /**
     * Loads saves from the SavesController.
     * @param playerName the player name
     * @param view the application view
     * @param start the consumer to start the game
     */
    public SavesControllerImpl(
        final String playerName,
        final View view,
        final Consumer<Save> start
    ) {
        this.start = start;
        this.run = controller -> view.displaySaves(controller);
        // load saves
        try {
            final var saveLoader = new SavesImpl(playerName);
            this.saves = saveLoader.loadSaves();
        } catch (final IOException e) {
           throw new UncheckedIOException("Error while loading saves", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Save> getSaves() {
        return Collections.unmodifiableList(this.saves);
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
    public void loadSave(final Save save) {
        this.start.accept(save);
    }
}
