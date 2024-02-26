package it.unibo.towerdefense.utils.savingloader;

import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.models.saving.Saving;

/**
 * Class implementing the GameLoader interface.
 */
public class SavingLoaderImpl implements SavingLoader {

    private static final String SAVED_GAMES_FOLDER = Constants.GAME_FOLDER +
            File.separator +
            "savings";

    private Logger logger;

    /**
     * Zero-argument constructor.
     */
    public SavingLoaderImpl() {
        // create the SAVED_GAMES_FOLDER if it does not exist
        final File folder = new File(SAVED_GAMES_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // create the logger
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    private String readPathContents(final Path path) {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            logger.error("Error reading file " + path.getFileName().toString(), e);
            return "";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Saving>> loadSavings() {
        // read all files from the SAVED_GAMES_FOLDER
        try (final Stream<Path> paths = Files.walk(Paths.get(SAVED_GAMES_FOLDER))) {
            // for each file, read the content and convert it to a Game object
            final List<Saving> games = paths
                .filter(Files::isRegularFile)
                .map(this::readPathContents)
                .filter((jsonData) -> !jsonData.isEmpty())
                .map(Saving::fromJson)
                .toList();
            // return the list of Game objects
            System.out.println(games);
            return Optional.of(games);
        } catch (final IOException e) {
            logger.error("Error loading saved games", e);
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeSaving(final Saving saving) {
        // convert the Game object to a JSON string
        final String jsonData = saving.toJSON();
        // create game name from current timestamp
        final String gameName = "game_" + System.currentTimeMillis() + ".json";
        // save the JSON string to a file in the SAVED_GAMES_FOLDER
        final Path path = Paths.get(SAVED_GAMES_FOLDER + File.separator + gameName);
        try {
            Files.writeString(path, jsonData, StandardCharsets.UTF_8);
            logger.info("Game saved to file " + path.getFileName().toString() + " successfully.");
            return true;
        } catch (final IOException e) {
            logger.error("Error saving game to file " + path.getFileName().toString(), e);
        }
        return false;
    }
}
