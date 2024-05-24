package it.unibo.towerdefense.model.saving;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import it.unibo.towerdefense.commons.utils.file.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the SavingsImpl class.
 */
class TestSavingsImpl {
    private static final String TEST_PLAYER_NAME = "TEST";
    private static final String RESOURCES_ROOT =
        "it/unibo/towerdefense/models/saving/";
    private static final String SAVING_FILE =
        RESOURCES_ROOT + "test-saving.json";

    private Savings savings;
    private String savingJson;

    /**
     * Configuration step: this is performed BEFORE each test.
     * @throws IOException if the file cannot be created
     * @throws URISyntaxException if the SAVING_FILE path is not correct
     */
    @BeforeEach
    void setUp(@TempDir() Path tempDir) throws IOException, URISyntaxException {
        // create a scoreboard implementation using a temporary directory
        this.savings = new SavingsImpl(
            TEST_PLAYER_NAME,
            tempDir.toAbsolutePath().toString()
        );
        // load the saving json from the file
        this.savingJson = FileUtils.readFile(
            Paths.get(
                ClassLoader.getSystemResource(SAVING_FILE).toURI()
            )
        );
    }

    /**
     * Test saving loading fromJson.
     */
    @Test
    void testFileSavingLoadFromJson() {
        // check that the saving is not null
        final var saving = this.loadSavingFromFile();
        Assertions.assertNotNull(saving);
    }

    /**
     * Test the writing and load of a saving.
     * @throws IOException if the saving cannot be loaded
     */
    @Test
    void testWriteLoadSaving() throws IOException {
        // create a new saving
        final Saving saving = this.loadSavingFromFile();
        // write the saving
        Assertions.assertTrue(this.savings.writeSaving(saving));
        // load all the savings
        final var savings = this.savings.loadSavings();
        // check that the saving is present
        Assertions.assertTrue(savings.contains(saving));
    }

    private Saving loadSavingFromFile() {
        // create a new saving from the JSON string
        return Saving.fromJson(savingJson);
    }
}
