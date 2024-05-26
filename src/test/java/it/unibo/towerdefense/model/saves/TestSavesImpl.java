package it.unibo.towerdefense.model.saves;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the SavesImpl class.
 */
@SuppressFBWarnings(
    value = "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
    justification = "Field is initialized in @BeforeEach method."
)
class TestSavesImpl {
    private static final String TEST_PLAYER_NAME = "TEST";
    private static final String RESOURCES_ROOT =
        "it/unibo/towerdefense/models/save/";
    private static final String SAVING_FILE =
        RESOURCES_ROOT + "test-save.json";

    private Saves saves;
    private String saveJson;

    /**
     * Configuration step: this is performed BEFORE each test.
     * @param tempDir the temporary directory
     * @throws IOException if the file cannot be created
     * @throws URISyntaxException if the SAVING_FILE path is not correct
     */
    @BeforeEach
    void setUp(final @TempDir() Path tempDir) throws IOException, URISyntaxException {
        // create a scoreboard implementation using a temporary directory
        this.saves = new SavesImpl(
            TEST_PLAYER_NAME,
            tempDir.toAbsolutePath().toString()
        );
        Assertions.assertNotNull(this.saves);
        // load the save json from the file
        this.saveJson = FileUtils.readFile(
            Paths.get(
                ClassLoader.getSystemResource(SAVING_FILE).toURI()
            )
        );
    }

    /**
     * Test save loading fromJson.
     */
    @Test
    void testFileSaveLoadFromJson() {
        // check that the save is not null
        final var save = this.loadSaveFromFile();
        Assertions.assertNotNull(save);
    }

    /**
     * Test the writing and load of a save.
     * @throws IOException if the save cannot be loaded
     */
    @Test
    void testWriteLoadSave() throws IOException {
        // create a new save
        final Save save = this.loadSaveFromFile();
        // write the save
        Assertions.assertTrue(this.saves.writeSave(save));
        // load all the saves
        final var saves = this.saves.loadSaves();
        // check that the save is present
        Assertions.assertTrue(saves.contains(save));
    }

    private Save loadSaveFromFile() {
        // create a new save from the JSON string
        return Save.fromJson(saveJson);
    }
}
