package it.unibo.towerdefense.model.scoreboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;

import it.unibo.towerdefense.commons.utils.file.FileUtils;
/**
 * Test class for the ScoreboardImpl class.
 */
class TestScoreboardImpl {

    private static final String NAME_1 = "test";
    private static final int WAVE_1 = 100;
    private static final String NAME_2 = "test2";
    private static final int WAVE_2 = 101;
    private static final String NAME_3 = "test3";
    private static final int WAVE_3 = 99;
    private static final String INVALID_JSON = "invalid json";

    private String filePath;
    private Scoreboard scoreboard;

    /**
     * Configuration step: this is performed BEFORE each test.
     * @throws IOException if the file cannot be created
     */
    @BeforeEach
    void setUp() throws IOException {
        // create a temporary file for the scoreboard
        this.filePath = File.createTempFile("scoreboard", ".json").getAbsolutePath();
        this.scoreboard = new ScoreboardImpl(this.filePath);
    }

    /**
     * Check that the loadScores() behavior with empty file.
     * @throws IOException if the file cannot be read
     */
    @Test
    void testLoadScores() throws IOException {
        this.scoreboard = new ScoreboardImpl(this.filePath);
        this.scoreboard.loadScores();
        Assertions.assertTrue(this.scoreboard.getScoreboard().isEmpty());
    }

    /**
     * Check that the saveScore() method works.
     * @throws IOException if the file cannot be read
     */
    @Test
    void testSaveScore() throws IOException {
        Assertions.assertTrue(this.scoreboard.saveScore(NAME_1, WAVE_1));
    }

    /**
     * Check that the loadScores() method works with multiple data.
     * Check that the scoreboard is in the correct order.
     * @throws IOException if the file cannot be read
     */
    @Test
    void testLoadScoresWithData() throws IOException {
        Assertions.assertTrue(this.scoreboard.saveScore(NAME_1, WAVE_1));
        Assertions.assertTrue(this.scoreboard.saveScore(NAME_2, WAVE_2));
        Assertions.assertTrue(this.scoreboard.saveScore(NAME_3, WAVE_3));
        // load the scoreboard
        this.scoreboard.loadScores();
        final var scores = this.scoreboard.getScoreboard();
        Assertions.assertEquals(3, scores.size());
        // check the order
        final var it = scores.iterator();
        Assertions.assertEquals(NAME_2, it.next().getName());
        Assertions.assertEquals(NAME_1, it.next().getName());
        Assertions.assertEquals(NAME_3, it.next().getName());
    }

    /**
     * Check that the loadScores() method works with invalid JSON.
     * @throws IOException the scoreboard file is not readable or corrupted
     */
    @Test
    void testInvalidJson() throws IOException {
        // write an invalid JSON string
        final var invalidJson = INVALID_JSON;
        // write the invalid JSON string to the file
        FileUtils.writeFile(this.filePath, invalidJson);
        // check loadScores() throws an IOException
        Assertions.assertThrows(IOException.class, () -> this.scoreboard.loadScores());
        Assertions.assertTrue(this.scoreboard.getScoreboard().isEmpty());
        // check saveScore() throws an IOException
        Assertions.assertThrows(
            IOException.class,
            () -> this.scoreboard.saveScore(NAME_1, WAVE_1)
        );
    }
}
