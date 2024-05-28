package it.unibo.towerdefense.model.enemies;

/**
 * Utility class containing the names of configuration files used by the classes
 * which need them.
 * Stored this way to access them from test classes.
 */
final class Filenames {
    /**
     * The folder containing all config files for this package.
     */
    private static final String ROOT = "src/main/resources/it/unibo/towerdefense/models/enemies/";
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
    static String wavesConfig() {
        return ROOT + WAVECONF;
    }

    /**
     * Returns the path of the types config file.
     *
     * @return the path of the types config file.
     */
    static String typesConfig() {
        return ROOT + TYPESCONF;
    }
}
