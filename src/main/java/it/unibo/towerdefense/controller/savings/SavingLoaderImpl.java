package it.unibo.towerdefense.controller.savings;

import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.model.saving.Saving;

/**
 * Class implementing the SavingLoader interface.
 */
public class SavingLoaderImpl implements SavingLoader {

    private static final String SAVED_GAMES_FOLDER = Constants.GAME_FOLDER
            + File.separator
            + "savings";

    private final Logger logger;
    private final String folderPath;

    /**
     * Constructor with player's name and file path.
     * @param playerName the name of the player
     * @param path the path of the folder containing the saved games
     * @throws IOException if the path cannot be created
     */
    public SavingLoaderImpl(final String playerName, final String path) throws IOException {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.folderPath = path + File.separator + playerName;
        // create the SAVED_GAMES_FOLDER if it does not exist
        FileUtils.createFolder(this.folderPath);
    }

    /**
     * Constructor with player's name.
     * The path is set to the default savings.
     * @param playerName the name of the player
     * @throws IOExceptions if the SAVED_GAMES_FOLDER cannot be created
     */
    public SavingLoaderImpl(final String playerName) throws IOException {
        this(playerName, SAVED_GAMES_FOLDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Saving> loadSavings() {
        // read all files from the folderPath
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            // for each file, read the content and convert it to a Game object
            return paths
                .filter(Files::isRegularFile)
                .map(FileUtils::readFileOptional)
                .flatMap(Optional::stream)
                .filter(StringUtils::isNotEmpty)
                .map(Saving::fromJson)
                .toList();
        } catch (final IOException e) {
            logger.error("Error loading saved games", e);
        }
        // return empty list
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeSaving(final Saving saving) {
        // convert the Saving object to a JSON string
        final String jsonData = saving.toJSON();
        // construct the file name
        final String filePath = this.folderPath + File.separator + saving.getName();
        // save the JSON string to file
        try {
            FileUtils.writeFile(filePath, jsonData);
        } catch (final IOException e) {
            logger.error("Error writing saving", e);
            return false;
        }
        return true;
    }
}
