package it.unibo.towerdefense.commons.utils.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Test class for the FileUtils class.
 */
class TestFileUtils {

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String TEST_DIR = TMP_DIR + File.separator + "test_folder";
    private static final String TEST_FILE = TMP_DIR + File.separator + "file.txt";
    private static final String TEST_CONTENT = "test content";

    /**
     * Setup test directories and files.
     */
    @BeforeEach
    void setUp() {
        // if TEST_FILE or TEST_DIR exists, delete them
        if (fileExists(TEST_DIR)) {
            deleteFile(TEST_DIR);
        }
        if (fileExists(TEST_FILE)) {
            deleteFile(TEST_FILE);
        }
    }


    /**
     * Test createFolder method.
     */
    @Test
    void testCreateFolder() {
        Assertions.assertDoesNotThrow(() -> FileUtils.createFolder(TEST_DIR));
        this.assertFileExists(TEST_DIR);
    }

    /**
     * Test createFile method.
     */
    @Test
    void testCreateFile() {
        Assertions.assertDoesNotThrow(() -> FileUtils.createFile(TEST_FILE));
        this.assertFileExists(TEST_FILE);
    }

    /**
     * Test readFile and writeFile methods.
     */
    @Test
    void testReadWriteFile() {
        Assertions.assertDoesNotThrow(() -> FileUtils.createFile(TEST_FILE));
        Assertions.assertDoesNotThrow(() -> FileUtils.writeFile(TEST_FILE, TEST_CONTENT));
        // read content and verify
        Assertions.assertDoesNotThrow(() ->
            Assertions.assertEquals(
                FileUtils.readFile(TEST_FILE),
                TEST_CONTENT
            )
        );
    }

    @Test
    void testReadFileOptional() {
        final Path path = Paths.get(TEST_FILE);
        Assertions.assertEquals(Optional.empty(), FileUtils.readFileOptional(path));
    }

    private boolean fileExists(final String path) {
        final File file = new File(path);
        return file.exists();
    }

    private void assertFileExists(final String path) {
        Assertions.assertTrue(fileExists(path));
    }

    private boolean deleteFile(final String path) {
        final File file = new File(path);
        return file.delete();
    }
}
