package it.unibo.towerdefense.model.enemies;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;

/**
 * Utility class containing the names of configuration files used by the classes
 * which need them.
 * Stored this way to access them from test classes.
 */
final class Filenames {
    /**
     * The folder containing all config files for this package.
     */
    private static final String ROOT = "it/unibo/towerdefense/models/enemies/";
    /**
     * The name of the waves config file.
     */
    private static final String WAVECONF = "waves.json";
    /**
     * The name of the types config file.
     */
    private static final String TYPESCONF = "types.json";

    /**
     * This is a utility class.
     */
    private Filenames() {
    }

    /**
     * Returns the path of the waves config file.
     *
     * @return the path of the waves config file.
     */
    static Path wavesConfig() {
        return getPath(ROOT + WAVECONF);
    }

    /**
     * Returns the path of the types config file.
     *
     * @return the path of the types config file.
     */
    static Path typesConfig() {
        return getPath(ROOT + TYPESCONF);
    }

    /**
     * Returns the path of the resource with name filename.
     *
     * @param filename the logical path to the resource starting from classpath
     * @return the actual path on the filesystem to the desired resource
     */
    private static Path getPath(final String filename) {
        try {
            return Paths.get(ClassLoader.getSystemResource(filename).toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            throw new UncheckedIOException(new IOException("Could not find file " + filename, e));
        }
    }
}
