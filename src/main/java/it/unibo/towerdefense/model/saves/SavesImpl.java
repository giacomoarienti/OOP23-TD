package it.unibo.towerdefense.model.saves;

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

/**
 * Class implementing the Saves interface.
 */
public class SavesImpl implements Saves {

    private static final String SAVED_GAMES_FOLDER = Constants.GAME_FOLDER
            + File.separator
            + "saves";

    private final Logger logger =
         LoggerFactory.getLogger(SavesImpl.class);
    private final String folderPath;

    /**
     * Constructor with player's name and file path.
     * @param playerName the name of the player
     * @param path the path of the folder containing the saved games
     * @throws IOException if the path cannot be created
     */
    public SavesImpl(final String playerName, final String path) throws IOException {
        this.folderPath = path + File.separator + playerName;
        // create the SAVED_GAMES_FOLDER if it does not exist
        FileUtils.createFolder(this.folderPath);
    }

    /**
     * Constructor with player's name.
     * The path is set to the default saves.
     * @param playerName the name of the player
     * @throws IOExceptions if the SAVED_GAMES_FOLDER cannot be created
     */
    public SavesImpl(final String playerName) throws IOException {
        this(playerName, SAVED_GAMES_FOLDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Save> loadSaves() {
        // read all files from the folderPath
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            // for each file, read the content and convert it to a Game object
            return paths
                .filter(Files::isRegularFile)
                .map(FileUtils::readFileOptional)
                .flatMap(Optional::stream)
                .filter(StringUtils::isNotEmpty)
                .map(Save::fromJson)
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
    public boolean writeSave(final Save save) {
        // convert the Save object to a JSON string
        final String jsonData = save.toJSON();
        // construct the file name
        final String filePath = this.folderPath + File.separator + save.getFileName();
        // save the JSON string to file
        try {
            FileUtils.writeFile(filePath, jsonData);
        } catch (final IOException e) {
            logger.error("Error writing save", e);
            return false;
        }
        return true;
    }
}
