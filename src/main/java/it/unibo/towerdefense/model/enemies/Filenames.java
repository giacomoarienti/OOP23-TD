package it.unibo.towerdefense.model.enemies;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class containing the names of configuration files used by the classes which
 * need them.
 * Stored this way to access them from test classes.
 */
final class Filenames {
    private static final String ROOT = "it/unibo/towerdefense/models/enemies/";
    private static final String WAVECONF = "waves.json";
    private static final String TYPESCONF = "types.json";

    private Filenames(){
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
    private static Path getPath(String filename){
        try {
            return Paths.get(ClassLoader.getSystemResource(filename).toURI());
        } catch (Throwable t) {
            throw new RuntimeException("Could not find file " + filename);
        }
    }
}
