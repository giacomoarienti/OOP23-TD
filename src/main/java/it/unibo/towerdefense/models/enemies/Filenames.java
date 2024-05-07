package it.unibo.towerdefense.models.enemies;

import java.nio.file.Path;

/**
 * Class containing the names of configuration files used by the classes which
 * need them.
 * Stored this way to access them from test classes.
 */
abstract class Filenames {
    static final String ROOT = "it/unibo/towerdefense/models/enemies/";
    static final String WAVECONF = "waves.json";
    static final String TYPESCONF = "types.json";
    static Path wavesConfig(){
        return Path.of(ClassLoader.getSystemResource(ROOT + WAVECONF).getPath());
    }
    static Path typesConfig(){
        return Path.of(ClassLoader.getSystemResource(ROOT + TYPESCONF).getPath());
    }
}
