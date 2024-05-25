package it.unibo.towerdefense.commons.utils.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
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
     * @throws IOException if folder could not be opened
     */
    @Test
    void testCreateFolder() throws IOException {
        FileUtils.createFolder(TEST_DIR);
        this.assertFileExists(TEST_DIR);
    }

    /**
     * Test createFile method.
     * @throws IOException if file could not be created
     */
    @Test
    void testCreateFile() throws IOException {
        FileUtils.createFile(TEST_FILE);
        this.assertFileExists(TEST_FILE);
    }

    /**
     * Test readFile and writeFile methods.
     * @throws IOException if the file could not be written or read.
     */
    @Test
    void testReadWriteFile() throws IOException {
        FileUtils.createFile(TEST_FILE);
        FileUtils.writeFile(TEST_FILE, TEST_CONTENT);
        // read content and verify
        final String content = FileUtils.readFile(TEST_FILE);
        Assertions.assertEquals(content, TEST_CONTENT);
    }

    @Test
    void testReadFileOptional() throws InvalidPathException {
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
